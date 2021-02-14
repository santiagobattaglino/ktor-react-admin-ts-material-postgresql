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
import jdbcat.ktor.example.db.model.Supplies
import jdbcat.ktor.example.db.model.Supply
import mu.KotlinLogging
import javax.sql.DataSource

class SupplyDao(private val dataSource: DataSource) {

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

    suspend fun insert(item: Supply) = dataSource.txRequired { connection ->
        val stmt = insertSqlTemplate
            .prepareStatement(
                connection = connection,
                returningColumnsOnUpdate = listOf(Supplies.id)
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
        val id: Int = stmt.generatedKeys.singleRow { it[Supplies.id] }
        item.copy(id = id)
    }

    suspend fun update(item: Supply) = dataSource.txRequired { connection ->
        val stmt = updateSqlTemplate
            .prepareStatement(
                connection = connection,
                returningColumnsOnUpdate = listOf(Supplies.id, Supplies.dateCreated)
            )
            .setColumns {
                item.copyValuesTo(it)
            }
        logger.debug { "update(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException(
                errorMessage = "Entity Supply id=${item.id} " +
                    "was not found and cannot be updated"
            )
        }
        val dateCreated = stmt.generatedKeys.singleRow { it[Supplies.dateCreated] }
        item.copy(dateCreated = dateCreated)
    }

    suspend fun select(id: Int) = dataSource.txRequired { connection ->
        val stmt = selectByIdSqlTemplate
            .prepareStatement(connection)
            .setColumns {
                it[Supplies.id] = id
            }
        logger.debug { "select(): $stmt" }
        stmt.executeQuery()
            .singleRowOrNull { Supply.extractFrom(it) }
            ?: throw EntityNotFoundException(errorMessage = "Supply id=$id cannot be found")
    }

    suspend fun selectAll() = dataSource.txRequired { connection ->
        val stmt = selectAllSqlTemplate.prepareStatement(connection)
        logger.debug { "selectAll(): $stmt" }
        stmt.executeQuery().asSequence().map {
            Supply.extractFrom(it)
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
                it[Supplies.id] = id
            }
        logger.debug { "delete(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException("Supply id=$id does not exist")
        }
    }

    companion object {

        private val createTableIfNotExistsSqlTemplate = sqlTemplate(Supplies) {
            """
            | CREATE TABLE IF NOT EXISTS $tableName (
            |   ${columns.sqlDefinitions}
            | )
            """
        }

        private val dropTableIfExistsSqlTemplate = sqlTemplate(Supplies) {
            "DROP TABLE IF EXISTS $tableName"
        }

        private val insertSqlTemplate = sqlTemplate(Supplies) {
            "INSERT INTO $tableName (${(columns - id).sqlNames}) VALUES (${(columns - id).sqlValues})"
        }

        private val updateSqlTemplate = sqlTemplate(Supplies) {
            """
            | UPDATE $tableName
            |   SET ${(columns - id - dateCreated).sqlAssignNamesToValues}
            |   WHERE $id = ${id.v}
            """
        }

        private val selectByIdSqlTemplate = sqlTemplate(Supplies) {
            "SELECT * FROM $tableName WHERE $id = ${id.v}"
        }

        private val selectAllSqlTemplate = sqlTemplate(Supplies) {
            "SELECT * FROM $tableName ORDER BY $name"
        }

        private val deleteById = sqlTemplate(Supplies) {
            "DELETE FROM $tableName WHERE $id = ${id.v}"
        }

        private val countAll = sqlTemplate(CounterResult, Supplies) { cr, e ->
            "SELECT COUNT(*) AS ${cr.counter} FROM ${e.tableName}"
        }

        object CounterResult : EphemeralTable() {
            val counter = integer("counter").nonnull()
        }
    }
}
