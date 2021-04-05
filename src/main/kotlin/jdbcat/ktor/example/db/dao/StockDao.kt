package jdbcat.ktor.example.db.dao

import jdbcat.core.*
import jdbcat.ktor.example.EntityNotFoundException
import jdbcat.ktor.example.db.model.*
import jdbcat.ktor.example.util.setRange
import mu.KotlinLogging
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

class StockDao(private val dataSource: DataSource) {

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

    suspend fun insert(item: Stock) = dataSource.txRequired { connection ->
        val stmt = insertSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(StockMovements.id)
                )
                .setColumns {
                    item.copyValuesTo(it)
                }
        // TODO cuando item.quantity < 0, crear un movimiento de stock con userId = showroom capilla
        logger.debug { "insert(): $stmt" }
        try {
            stmt.executeUpdate()
        } catch (e: SQLException) {
            /*if (e.hasState(PSQLState.UNIQUE_VIOLATION)) {
                logger.debug { "insert(): PSQLState.UNIQUE_VIOLATION, updating" }
                update(item)
            }*/
            e.printStackTrace()
        }
        val id: Int = stmt.generatedKeys.singleRow { it[StockMovements.id] }
        item.copy(id = id)
    }

    suspend fun update(item: Stock) = dataSource.txRequired { connection ->
        val stmt = updateSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(StockMovements.id, StockMovements.dateCreated)
                )
                .setColumns {
                    item.copyValuesTo(it)
                }
        logger.debug { "update(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException(
                    errorMessage = "Entity Stock id=${item.id} " +
                            "was not found and cannot be updated"
            )
        }
        val dateCreated = stmt.generatedKeys.singleRow { it[StockMovements.dateCreated] }
        item.copy(dateCreated = dateCreated)
    }

    suspend fun select(id: Int) = dataSource.txRequired { connection ->
        val stmt = selectByIdSqlTemplate
                .prepareStatement(connection)
                .setColumns {
                    it[StockMovements.id] = id
                }
        logger.debug { "select($id): $stmt" }
        stmt.executeQuery()
                .singleRowOrNull { Stock.extractFrom(it) }
                ?: throw EntityNotFoundException(errorMessage = "Stock id=$id cannot be found")
    }

    private fun buildSelectAllSqlTemplate(filter: Filter?, range: List<Int>?, sort: List<String>?, connection: Connection): TemplatizeStatement {
        return if (sort != null) {
            // TODO build filter and range
            sqlTemplate(StockMovements) {
                """
                | SELECT *
                |   FROM $tableName 
                |   ORDER BY ${sort[0]} ${sort[1]}
                |   LIMIT ? OFFSET ?
                """
            }.prepareStatement(connection)
        } else {
            sqlTemplate(StockMovements) {
                """
                | SELECT *
                |   FROM $tableName 
                |   ORDER BY $id DESC
                |   LIMIT ? OFFSET ?
                """
            }.prepareStatement(connection)
        }
    }

    suspend fun selectAll(filter: Filter?, range: List<Int>?, sort: List<String>?) = dataSource.txRequired { connection ->
        var stmt: TemplatizeStatement = buildSelectAllSqlTemplate(filter, range, sort, connection)

        range?.let { range ->
            setRange(range, stmt)
        }

        filter?.let { filter ->
            filter.productId?.let { productId ->
                stmt = selectByProductIdSqlTemplate.prepareStatement(connection)
                stmt.setColumns {
                    it[StockMovements.productId] = productId
                }

                range?.let { range ->
                    setRange(range, stmt, 2, 3)
                }
            }

            filter.userId?.let { userId ->
                stmt = selectByUserIdSqlTemplate.prepareStatement(connection)
                stmt.setColumns {
                    it[StockMovements.userId] = userId
                }

                range?.let { range ->
                    setRange(range, stmt, 2, 3)
                }
            }

            if (filter.productId != null && filter.userId != null) {
                stmt = selectByProductIdAndUserIdSqlTemplate.prepareStatement(connection)
                stmt.setColumns {
                    it[StockMovements.productId] = filter.productId
                    it[StockMovements.userId] = filter.userId
                }

                range?.let { range ->
                    setRange(range, stmt, 3, 4)
                }
            }

            filter.id?.let { id ->
                stmt = selectByIdsSqlTemplate.prepareStatement(connection)
                stmt.setArray(1, connection.createArrayOf("INT", id.toTypedArray()))
            }

            logger.debug { "selectAll() by productId, userId, id paged: $stmt" }
        }

        stmt.executeQuery().asSequence().map {
            Stock.extractFrom(it)
        }
    }

    suspend fun selectReport() =
            dataSource.txRequired { connection ->
                val stmt = selectReportSqlTemplate.prepareStatement(connection)
                logger.debug { "selectReport(): $stmt" }
                stmt.executeQuery().asSequence().map {
                    StockReport.extractFrom(it)
                }
            }

    suspend fun selectByUserId(userId: Int) = dataSource.txRequired { connection ->
        val stmt = selectByUserIdSumSqlTemplate
                .prepareStatement(connection)
                .setColumns {
                    it[StockMovements.userId] = userId
                }
        // stmt.setInt(1, userId)

        logger.debug { "selectByUserId($userId): $stmt" }
        stmt.executeQuery().asSequence().map {
            StockByUser.extractFrom(it)
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
                    it[StockMovements.id] = id
                }
        logger.debug { "delete(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException("Stock id=$id does not exist")
        }
    }

    companion object {

        private val createTableIfNotExistsSqlTemplate = sqlTemplate(StockMovements) {
            """
            | CREATE TABLE IF NOT EXISTS $tableName (
            |   ${columns.sqlDefinitions}
            | )
            """
        }

        private val dropTableIfExistsSqlTemplate = sqlTemplate(StockMovements) {
            "DROP TABLE IF EXISTS $tableName"
        }

        private val insertSqlTemplate = sqlTemplate(StockMovements) {
            "INSERT INTO $tableName (${(columns - id).sqlNames}) VALUES (${(columns - id).sqlValues})"
        }

        private val updateSqlTemplate = sqlTemplate(StockMovements) {
            """
            | UPDATE $tableName
            |   SET ${(columns - id - dateCreated).sqlAssignNamesToValues}
            |   WHERE $id = ${id.v}
            """
        }

        private val selectByIdSqlTemplate = sqlTemplate(StockMovements) {
            /*"""
            | SELECT MAX(id) AS id, product_id, user_id, SUM(t1) AS t1, SUM(t2) AS t2, SUM(t3) AS t3,
            |   SUM(t4) AS t4, SUM(t5) AS t5, SUM(t6) AS t6, SUM(t7) AS t7, SUM(t8) AS t8, SUM(t9) AS t9,
            |   SUM(t10) AS t10, SUM(t11) AS t11,
            |   SUM(total) AS total, MAX(notes) AS notes, MAX(date_created) AS date_created
            |   FROM $tableName
            |   WHERE $productId = (SELECT $productId FROM $tableName WHERE $id = ${id.v})
            |   AND $userId = (SELECT $userId FROM $tableName WHERE $id = ${id.v})
            |   GROUP BY $productId, $userId
            """*/
            "SELECT * FROM $tableName WHERE $id = ${id.v}"
        }

        private val selectByUserIdSumSqlTemplate = sqlTemplate(StockMovements) {
            """
            | SELECT MAX($id) AS ${id.name}, $productId, SUM(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) AS total 
            |   FROM $tableName 
            |   WHERE $userId = ${userId.v} 
            |   GROUP BY $productId 
            |   ORDER BY $productId
            """
        }

        private val selectReportSqlTemplate = sqlTemplate(StockMovements) {
            """
            | select id, product_id,
            |   sum(t1) - COALESCE((
            |      select sum(quantity) as t1_sales 
            |       from sale_products 
            |       where size = 1 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t1,
            |   sum(t2) - COALESCE((
            |       select sum(quantity) as t2_sales 
            |       from sale_products 
            |       where size = 2 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t2,
            |   sum(t3) - COALESCE((
            |       select sum(quantity) as t3_sales 
            |       from sale_products 
            |       where size = 3 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t3,
            |   sum(t4) - COALESCE((
            |       select sum(quantity) as t4_sales 
            |       from sale_products 
            |       where size = 4 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t4,
            |   sum(t5) - COALESCE((
            |       select sum(quantity) as t5_sales 
            |       from sale_products 
            |       where size = 5 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t5,
            |   sum(t6) - COALESCE((
            |       select sum(quantity) as t6_sales 
            |       from sale_products 
            |       where size = 6 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t6,
            |   sum(t7) - COALESCE((
            |       select sum(quantity) as t7_sales 
            |       from sale_products 
            |       where size = 7 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t7,
            |   sum(t8) - COALESCE((
            |       select sum(quantity) as t8_sales 
            |       from sale_products 
            |       where size = 8 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t8,
            |   sum(t9) - COALESCE((
            |       select sum(quantity) as t9_sales 
            |       from sale_products 
            |       where size = 9 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t9,
            |   sum(t10) - COALESCE((
            |       select sum(quantity) as t10_sales 
            |       from sale_products 
            |       where size = 10 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t10,
            |   sum(t11) - COALESCE((
            |       select sum(quantity) as t11_sales 
            |       from sale_products 
            |       where size = 11 and product_id = product_id
            |       group by product_id LIMIT 1
            |   ), 0) as t11
            |   from stock_movements 
            |   group by id, product_id
            |   order by product_id DESC
            """
        }

        private val selectByIdsSqlTemplate = sqlTemplate(StockMovements) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE $id = ANY (?)
            |   ORDER BY $id DESC
            """
        }

        private val selectByProductIdAndUserIdSqlTemplate = sqlTemplate(StockMovements) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE $productId = ${productId.v}
            |   AND $userId = ${userId.v}
            |   ORDER BY $id DESC
            |   LIMIT ? OFFSET ?
            """
        }

        private val selectByProductIdSqlTemplate = sqlTemplate(StockMovements) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE $productId = ${productId.v}
            |   ORDER BY $id DESC
            |   LIMIT ? OFFSET ?
            """
        }

        private val selectByUserIdSqlTemplate = sqlTemplate(StockMovements) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE $userId = ${userId.v}
            |   ORDER BY $id DESC
            |   LIMIT ? OFFSET ?
            """
        }

        private val deleteById = sqlTemplate(StockMovements) {
            /*"""
            | DELETE FROM $tableName 
            |   WHERE $productId = (SELECT $productId FROM $tableName WHERE $id = ${id.v})
            |   AND $userId = (SELECT $userId FROM $tableName WHERE $id = ${id.v}) 
            """*/
            "DELETE FROM $tableName WHERE $id = ${id.v}"
        }

        private val countAll = sqlTemplate(CounterResult, StockMovements) { cr, e ->
            "SELECT COUNT(*) AS ${cr.counter} FROM ${e.tableName}"
        }

        object CounterResult : EphemeralTable() {
            val counter = integer("counter").nonnull()
        }
    }
}
