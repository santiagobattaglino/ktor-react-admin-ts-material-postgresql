package jdbcat.ktor.example.db.dao

import jdbcat.core.EphemeralTable
import jdbcat.core.asSequence
import jdbcat.core.integer
import jdbcat.core.singleRow
import jdbcat.core.singleRowOrNull
import jdbcat.core.sqlAssignNamesToValues
import jdbcat.core.sqlDefinitions
import jdbcat.core.sqlNames
import jdbcat.core.sqlTemplate
import jdbcat.core.sqlValues
import jdbcat.core.txRequired
import jdbcat.ktor.example.EntityNotFoundException
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

    suspend fun selectAll() = dataSource.txRequired { connection ->
        val stmt = selectAllSqlTemplate.prepareStatement(connection)
        logger.debug { "selectAll(): $stmt" }
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
            |   ${columns.sqlDefinitions},
            |   UNIQUE ( $name )
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

        private val selectByIdSqlTemplate = sqlTemplate(Products) {
            "SELECT * FROM $tableName WHERE $id = ${id.v}"
        }

        private val selectAllSqlTemplate = sqlTemplate(Products) {
            "SELECT * FROM $tableName ORDER BY $name"
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
