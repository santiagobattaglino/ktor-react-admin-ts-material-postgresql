package jdbcat.ktor.example.db.dao

import jdbcat.core.*
import jdbcat.ktor.example.EntityNotFoundException
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.db.model.Sale
import jdbcat.ktor.example.db.model.SaleAliasFields
import jdbcat.ktor.example.db.model.Sales
import jdbcat.ktor.example.util.setRange
import mu.KotlinLogging
import java.sql.Connection
import javax.sql.DataSource

class SaleDao(private val dataSource: DataSource) {

    private val logger = KotlinLogging.logger { }

    suspend fun createTableIfNotExists() = dataSource.txRequired { connection ->
        val stmt = createTableIfNotExistsSqlTemplate.prepareStatement(connection)
        logger.debug { "createTableIfNotExists(): $stmt" }
        stmt.executeUpdate()
    }

    suspend fun dropTableIfExists() = dataSource.txRequired { connection ->
        val stmt = dropTableIfExistsSqlTemplate.prepareStatement(connection)
        logger.debug { "dropTableIfExists(): $stmt" }
        stmt.executeUpdate()
    }

    suspend fun insert(item: Sale) = dataSource.txRequired { connection ->
        val stmt = insertSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(Sales.id)
                )
                .setColumns {
                    item.copyValuesTo(it)
                }
        logger.debug { "insert(): $stmt" }
        try {
            stmt.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val id: Int = stmt.generatedKeys.singleRow { it[Sales.id] }
        item.copy(id = id)
    }

    suspend fun update(item: Sale) = dataSource.txRequired { connection ->
        val stmt = updateSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(Sales.id, Sales.dateCreated)
                )
                .setColumns {
                    item.copyValuesTo(it)
                }
        logger.debug { "update(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException(
                    errorMessage = "Entity Sale id=${item.id} " +
                            "was not found and cannot be updated"
            )
        }
        val dateCreated = stmt.generatedKeys.singleRow { it[Sales.dateCreated] }
        item.copy(dateCreated = dateCreated)
    }

    suspend fun select(id: Int) = dataSource.txRequired { connection ->
        val stmt = selectByIdSqlTemplate
                .prepareStatement(connection)
        stmt.setInt(1, id)

        logger.debug { "select($id): $stmt" }
        stmt.executeQuery()
                .singleRowOrNull { Sale.extractFrom(it) }
                ?: throw EntityNotFoundException(errorMessage = "Sale id=$id cannot be found")
    }

    private fun buildSelectAllSqlTemplate(sort: List<String>?, connection: Connection): TemplatizeStatement {
        return if (sort != null) {
            sqlTemplate(Sales) {
                """
                |   select *, COALESCE((
                |   select sum(cost_x_quantity) from (
                |        select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * manufacturing_cost) END as cost_x_quantity
                |        from sale_products join products
                |        on sale_products.product_id = products.id
                |        join sales
                |        on sale_products.sale_id = sales.id
                |        group by quantity, manufacturing_cost, custom_price
                |    ) as cost_x_quantity
                |  ), 0) as total 
                |  from sales
                |   ORDER BY ${sort[0]} ${sort[1]}
                |   LIMIT ? OFFSET ?
                """
            }.prepareStatement(connection)
        } else {
            sqlTemplate(Sales) {
                """
                |   select *, COALESCE((
                |   select sum(cost_x_quantity) from (
                |        select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * manufacturing_cost) END as cost_x_quantity
                |        from sale_products join products
                |        on sale_products.product_id = products.id
                |        join sales
                |        on sale_products.sale_id = sales.id
                |        group by quantity, manufacturing_cost, custom_price
                |    ) as cost_x_quantity
                |  )), 0 as total 
                |  from sales
                |   ORDER BY $id DESC
                |   LIMIT ? OFFSET ?
                """
            }.prepareStatement(connection)
        }
    }

    suspend fun selectAll(filter: Filter?, range: List<Int>?, sort: List<String>?) = dataSource.txRequired { connection ->
        var stmt: TemplatizeStatement = buildSelectAllSqlTemplate(sort, connection)

        range?.let { range ->
            setRange(range, stmt)
            logger.debug { "selectAll() paged: $stmt" }
        }

        filter?.let { filter ->
            filter.sellerId?.let { sellerId ->
                stmt = selectBySellerIdSqlTemplate.prepareStatement(connection)
                        .setColumns {
                            it[Sales.sellerId] = sellerId
                        }

                range?.let { range ->
                    setRange(range, stmt, 2, 3)
                    logger.debug { "selectAll() by sellerId, paged: $stmt" }
                }
            }

            filter.q?.let {
                stmt = selectByQueryIdSqlTemplate.prepareStatement(connection)

                stmt.setString(1, "%$it%")
                stmt.setString(2, "%$it%")

                range?.let { range ->
                    setRange(range, stmt, 3, 4)
                    logger.debug { "selectAll() by query, paged: $stmt" }
                }
            }

            filter.id?.let { id ->
                stmt = selectByIdsSqlTemplate.prepareStatement(connection)

                stmt.setArray(1, connection.createArrayOf("INT", id.toTypedArray()))
                logger.debug { "selectAll() by ANY(ids): $stmt" }
            }
        }

        stmt.executeQuery().asSequence().map {
            Sale.extractFrom(it)
        }
    }

    suspend fun countAll() = dataSource.txRequired { connection ->
        val stmt = countAll.prepareStatement(connection)
        logger.debug { "countAll(): $stmt" }
        stmt.executeQuery().singleRow { it[CounterResult.counter] }
    }

    suspend fun delete(id: Int) = dataSource.txRequired { connection ->
        val stmt = deleteById
                .prepareStatement(connection)
                .setColumns {
                    it[Sales.id] = id
                }
        logger.debug { "delete(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException("Sale id=$id does not exist")
        }
    }

    companion object {
        private val createTableIfNotExistsSqlTemplate = sqlTemplate(Sales) {
            """
            | CREATE TABLE IF NOT EXISTS $tableName (
            |   ${columns.sqlDefinitions}
            | )
            """
        }

        private val dropTableIfExistsSqlTemplate = sqlTemplate(Sales) {
            "DROP TABLE IF EXISTS $tableName"
        }

        private val insertSqlTemplate = sqlTemplate(Sales) {
            "INSERT INTO $tableName (${(columns - id).sqlNames}) VALUES (${(columns - id).sqlValues})"
        }

        private val updateSqlTemplate = sqlTemplate(Sales) {
            """
            | UPDATE $tableName
            |   SET ${(columns - id - dateCreated).sqlAssignNamesToValues}
            |   WHERE $id = ${id.v}
            """
        }

        private val selectByIdSqlTemplate = sqlTemplate(Sales) {
            """
            |  select *, COALESCE((
            |   select sum(cost_x_quantity) from (
            |        select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * manufacturing_cost) END as cost_x_quantity
            |        from sale_products join products
            |        on sale_products.product_id = products.id
            |        join sales
            |        on sale_products.sale_id = sales.id
            |        group by quantity, manufacturing_cost, custom_price
            |    ) as cost_x_quantity
            |  )), 0 as total 
            |  from sales
            |  WHERE sales.id = ${id.v}
            """
        }

        private val selectBySellerIdSqlTemplate = sqlTemplate(Sales) {
            """
            |  select *, COALESCE((
            |   select sum(cost_x_quantity) from (
            |        select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * manufacturing_cost) END as cost_x_quantity
            |        from sale_products join products
            |        on sale_products.product_id = products.id
            |        join sales
            |        on sale_products.sale_id = sales.id
            |        group by quantity, manufacturing_cost, custom_price
            |    ) as cost_x_quantity
            |  ), 0) as total 
            |  from $tableName
            |  WHERE $sellerId = ${sellerId.v}
            |  ORDER BY $id DESC
            |  LIMIT ? OFFSET ?
            """
        }

        private val selectByQueryIdSqlTemplate = sqlTemplate(Sales) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE CAST($id AS TEXT) ILIKE ?
            |   OR name ILIKE ?
            |   ORDER BY $id DESC
            |   LIMIT ? OFFSET ?
            """
        }

        private val selectByIdsSqlTemplate = sqlTemplate(Sales) {
            """
            |  select *, COALESCE((
            |   select sum(cost_x_quantity) from (
            |        select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * manufacturing_cost) END as cost_x_quantity
            |        from sale_products join products
            |        on sale_products.product_id = products.id
            |        join sales
            |        on sale_products.sale_id = sales.id
            |        group by quantity, manufacturing_cost, custom_price
            |    ) as cost_x_quantity
            |  ), 0) as total 
            |  from $tableName
            |  WHERE $id = ANY (?)
            |  ORDER BY $id DESC
            """
        }

        private val deleteById = sqlTemplate(Sales) {
            "DELETE FROM $tableName WHERE $id = ${id.v}"
        }

        private val countAll = sqlTemplate(CounterResult, Sales) { cr, e ->
            "SELECT COUNT(*) AS ${cr.counter} FROM ${e.tableName}"
        }

        object CounterResult : EphemeralTable() {
            val counter = integer("counter").nonnull()
        }
    }
}
