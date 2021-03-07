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
import jdbcat.ktor.example.db.model.Stock
import jdbcat.ktor.example.db.model.StockByUser
import jdbcat.ktor.example.db.model.StockMovements
import mu.KotlinLogging
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
        logger.debug { "insert(): $stmt" }
        try {
            stmt.executeUpdate()
        } catch (e: Exception) {
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

    suspend fun selectAll(range: List<Int>?, sort: List<String>?) =
        dataSource.txRequired { connection ->
            val stmt = selectAllSqlTemplate.prepareStatement(connection)
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
            stmt.executeQuery().asSequence().map {
                Stock.extractFrom(it)
            }
        }

    suspend fun selectByUserId(userId: Int) = dataSource.txRequired { connection ->
        val stmt = selectByUserIdSqlTemplate
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
            "SELECT * FROM $tableName WHERE $id = ${id.v}"
        }

        private val selectByUserIdSqlTemplate = sqlTemplate(StockMovements) {
            "SELECT MAX($id) AS ${id.name}, $productId, SUM($quantity) AS ${quantity.name} FROM $tableName WHERE $userId = ${userId.v} GROUP BY $productId ORDER BY $productId"
        }

        private val selectAllSqlTemplate = sqlTemplate(StockMovements) {
            "SELECT * FROM $tableName ORDER BY $productId LIMIT ? OFFSET ?"
        }

        private val deleteById = sqlTemplate(StockMovements) {
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
