package jdbcat.ktor.example.db.dao

import jdbcat.core.*
import jdbcat.ktor.example.EntityNotFoundException
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.db.model.Product
import jdbcat.ktor.example.db.model.Products
import mu.KotlinLogging
import javax.sql.DataSource

class ProductDao(private val dataSource: DataSource) {

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

    suspend fun insert(item: Product) = dataSource.txRequired { connection ->
        val stmt = insertSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(Products.id)
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
        val productId: Int = stmt.generatedKeys.singleRow { it[Products.id] }
        item.copy(id = productId)
    }

    suspend fun update(item: Product) = dataSource.txRequired { connection ->
        val stmt = updateSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(Products.id, Products.dateCreated)
                )
                .setColumns {
                    item.copyValuesTo(it)
                }
        logger.debug { "update(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException(
                    errorMessage = "Entity Product id=${item.id} " +
                            "was not found and cannot be updated"
            )
        }
        val dateCreated = stmt.generatedKeys.singleRow { it[Products.dateCreated] }
        item.copy(dateCreated = dateCreated)
    }

    suspend fun select(id: Int) = dataSource.txRequired { connection ->
        val stmt = selectByIdSqlTemplate
                .prepareStatement(connection)
                .setColumns {
                    it[Products.id] = id
                }
        logger.debug { "selectById(): $stmt" }
        stmt.executeQuery()
                .singleRowOrNull { Product.extractFrom(it) }
                ?: throw EntityNotFoundException(errorMessage = "Product id=$id cannot be found")
    }

    suspend fun selectAll(filter: Filter?, range: List<Int>?, sort: List<String>?) = dataSource.txRequired { connection ->
        var stmt: TemplatizeStatement = selectAllSqlTemplate.prepareStatement(connection)

        range?.let { range ->
            stmt.setInt(1, range[1] - range[0] + 1)
            if (range[0] == 0) {
                stmt.setInt(2, range[0])
            } else {
                stmt.setInt(2, range[0] + 1)
            }
            logger.debug { "selectAll() paged: $stmt" }
        }

        filter?.let { filter ->
            filter.catId?.let { catId ->
                stmt = selectByCatIdSqlTemplate.prepareStatement(connection)
                        .setColumns {
                            it[Products.catId] = catId
                        }

                range?.let { range ->
                    stmt.setInt(2, range[1] - range[0] + 1)
                    if (range[0] == 0) {
                        stmt.setInt(3, range[0])
                    } else {
                        stmt.setInt(3, range[0] + 1)
                    }
                }
                logger.debug { "selectAll() by catId, paged: $stmt" }
            }

            filter.q?.let {
                stmt = selectByQueryIdSqlTemplate.prepareStatement(connection)

                stmt.setString(1, "%$it%")
                stmt.setString(2, "%$it%")

                range?.let { range ->
                    stmt.setInt(3, range[1] - range[0] + 1)
                    if (range[0] == 0) {
                        stmt.setInt(4, range[0])
                    } else {
                        stmt.setInt(4, range[0] + 1)
                    }
                }
                logger.debug { "selectAll() by query, paged: $stmt" }
            }

            filter.id?.let { id ->
                stmt = selectByIdsSqlTemplate.prepareStatement(connection)

                stmt.setArray(1, connection.createArrayOf("INT", id.toTypedArray()))
                logger.debug { "selectAll() by ANY(ids), paged: $stmt" }
            }
        }

        stmt.executeQuery().asSequence().map {
            Product.extractFrom(it)
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
                    it[Products.id] = id
                }
        logger.debug { "deleteById(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException("Product id=$id does not exist")
        }
    }

    companion object {

        private val createTableIfNotExistsSqlTemplate = sqlTemplate(Products) {
            """
            | CREATE TABLE IF NOT EXISTS $tableName (
            |   ${columns.sqlDefinitions}
            | )
            """
        }

        private val dropTableIfExistsSqlTemplate = sqlTemplate(Products) {
            "DROP TABLE IF EXISTS $tableName"
        }

        private val insertSqlTemplate = sqlTemplate(Products) {
            "INSERT INTO $tableName (${(columns - id).sqlNames}) VALUES (${(columns - id).sqlValues})"
        }

        private val updateSqlTemplate = sqlTemplate(Products) {
            """
            | UPDATE $tableName
            |   SET ${(columns - id - dateCreated).sqlAssignNamesToValues}
            |   WHERE $id = ${id.v}
            """
        }

        private val selectAllSqlTemplate = sqlTemplate(Products) {
            """
            | SELECT *
            |   FROM $tableName 
            |   ORDER BY $id DESC
            |   LIMIT ? OFFSET ?
            """
        }

        private val selectByIdSqlTemplate = sqlTemplate(Products) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE products.id = ${id.v}
            """
        }

        private val selectByIdsSqlTemplate = sqlTemplate(Products) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE $id = ANY (?)
            |   ORDER BY $id DESC
            """
        }

        private val selectByCatIdSqlTemplate = sqlTemplate(Products) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE $catId = ${catId.v}
            |   ORDER BY $id DESC
            |   LIMIT ? OFFSET ?
            """
        }

        private val selectByQueryIdSqlTemplate = sqlTemplate(Products) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE CAST($id AS TEXT) ILIKE ?
            |   OR name ILIKE ?
            |   ORDER BY $id DESC
            |   LIMIT ? OFFSET ?
            """
        }

        private val deleteById = sqlTemplate(Products) {
            "DELETE FROM $tableName WHERE $id = ${id.v}"
        }

        private val countAll = sqlTemplate(CounterResult, Products) { cr, e ->
            "SELECT COUNT(*) AS ${cr.counter} FROM ${e.tableName}"
        }

        object CounterResult : EphemeralTable() {
            val counter = integer("counter").nonnull()
        }
    }
}
