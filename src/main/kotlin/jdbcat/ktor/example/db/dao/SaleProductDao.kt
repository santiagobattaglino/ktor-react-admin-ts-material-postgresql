package jdbcat.ktor.example.db.dao

import jdbcat.core.*
import jdbcat.ktor.example.EntityNotFoundException
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.db.model.SaleProduct
import jdbcat.ktor.example.db.model.SaleProducts
import mu.KotlinLogging
import javax.sql.DataSource

class SaleProductDao(private val dataSource: DataSource) {

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

    suspend fun insert(item: SaleProduct) = dataSource.txRequired { connection ->
        val stmt = insertSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(SaleProducts.id)
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
        val id: Int = stmt.generatedKeys.singleRow { it[SaleProducts.id] }
        item.copy(id = id)
    }

    suspend fun update(item: SaleProduct) = dataSource.txRequired { connection ->
        val stmt = updateSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(SaleProducts.id, SaleProducts.dateCreated)
                )
                .setColumns {
                    item.copyValuesTo(it)
                }
        logger.debug { "update(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException(
                    errorMessage = "Entity SaleProduct id=${item.id} " +
                            "was not found and cannot be updated"
            )
        }
        val dateCreated = stmt.generatedKeys.singleRow { it[SaleProducts.dateCreated] }
        item.copy(dateCreated = dateCreated)
    }

    suspend fun select(id: Int) = dataSource.txRequired { connection ->
        val stmt = selectByIdSqlTemplate
                .prepareStatement(connection)
                .setColumns {
                    it[SaleProducts.id] = id
                }
        logger.debug { "select($id): $stmt" }
        stmt.executeQuery()
                .singleRowOrNull { SaleProduct.extractFrom(it) }
                ?: throw EntityNotFoundException(errorMessage = "SaleProduct id=$id cannot be found")
    }

    suspend fun selectAll(filter: Filter?, range: List<Int>?, sort: List<String>?) = dataSource.txRequired { connection ->
        val stmt: TemplatizeStatement
        if (filter?.saleId != null) {
            stmt = selectBySaleIdSqlTemplate.prepareStatement(connection)
                    .setColumns {
                        it[SaleProducts.saleId] = filter.saleId
                    }
            logger.debug { "selectAll() by saleId: $stmt" }
        } else {
            stmt = selectAllSqlTemplate.prepareStatement(connection)
            range?.let {
                stmt.setInt(1, it[1] - it[0] + 1) // LIMIT
                // OFFSET
                if (it[0] == 0) {
                    stmt.setInt(2, it[0])
                } else {
                    stmt.setInt(2, it[0] + 1)
                }
            }
            logger.debug { "selectAll(): $stmt" }
        }

        stmt.executeQuery().asSequence().map {
            SaleProduct.extractFrom(it)
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
                    it[SaleProducts.id] = id
                }
        logger.debug { "delete(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException("SaleProduct id=$id does not exist")
        }
    }

    companion object {

        private val createTableIfNotExistsSqlTemplate = sqlTemplate(SaleProducts) {
            """
            | CREATE TABLE IF NOT EXISTS $tableName (
            |   ${columns.sqlDefinitions}
            | )
            """
        }

        private val dropTableIfExistsSqlTemplate = sqlTemplate(SaleProducts) {
            "DROP TABLE IF EXISTS $tableName"
        }

        private val insertSqlTemplate = sqlTemplate(SaleProducts) {
            "INSERT INTO $tableName (${(columns - id).sqlNames}) VALUES (${(columns - id).sqlValues})"
        }

        private val updateSqlTemplate = sqlTemplate(SaleProducts) {
            """
            | UPDATE $tableName
            |   SET ${(columns - id - dateCreated).sqlAssignNamesToValues}
            |   WHERE $id = ${id.v}
            """
        }

        private val selectByIdSqlTemplate = sqlTemplate(SaleProducts, PriceFields) { saleProducts, priceFields ->
            """
            | select sale_products.*, sales.payment_method_id, sales.price_id, products.manufacturing_cost,
            |   (select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * products.manufacturing_cost) END as manufacturing_cost_total)
            |   from ${saleProducts.tableName} 
            |   join sales on sale_products.sale_id = sales.id
            |   join products on sale_products.product_id = products.id
            |   WHERE sale_products.id = ${saleProducts.id.v}
            |   group by sale_products.id, sales.payment_method_id, sales.price_id, products.manufacturing_cost
            """
        }

        object PriceFields : EphemeralTable() {
            val paymentMethodId = integer("payment_methid_id").nonnull()
            val priceId = integer("price_id").nonnull()
            val manufacturingCost = integer("manufacturing_cost").nonnull()
            val manufacturingCostTotal = integer("manufacturing_cost_total").nonnull()
        }

        private val selectAllSqlTemplate = sqlTemplate(SaleProducts, PriceFields) { saleProducts, priceFields ->
            """
            | select sale_products.*, sales.payment_method_id, sales.price_id, products.manufacturing_cost,
            |   (select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * products.manufacturing_cost) END as manufacturing_cost_total)
            |   from ${saleProducts.tableName} 
            |   join sales on sale_products.sale_id = sales.id
            |   join products on sale_products.product_id = products.id
            |   group by sale_products.id, sales.payment_method_id, sales.price_id, products.manufacturing_cost
            |   ORDER BY ${saleProducts.id} DESC LIMIT ? OFFSET ?
            """
        }

        private val selectBySaleIdSqlTemplate = sqlTemplate(SaleProducts, PriceFields) { saleProducts, priceFields ->
            """
            | select sale_products.*, sales.payment_method_id, sales.price_id, products.manufacturing_cost,
            |   (select CASE WHEN custom_price NOTNULL THEN sum(quantity * custom_price) ELSE sum(quantity * products.manufacturing_cost) END as manufacturing_cost_total)
            |   from ${saleProducts.tableName} 
            |   join sales on sale_products.sale_id = sales.id
            |   join products on sale_products.product_id = products.id
            |   WHERE sale_products.sale_id = ${saleProducts.saleId.v}
            |   group by sale_products.id, sales.payment_method_id, sales.price_id, products.manufacturing_cost
            |   ORDER BY ${saleProducts.id} DESC
            """
        }

        private val deleteById = sqlTemplate(SaleProducts) {
            "DELETE FROM $tableName WHERE $id = ${id.v}"
        }

        private val countAll = sqlTemplate(CounterResult, SaleProducts) { cr, e ->
            "SELECT COUNT(*) AS ${cr.counter} FROM ${e.tableName}"
        }

        object CounterResult : EphemeralTable() {
            val counter = integer("counter").nonnull()
        }
    }
}
