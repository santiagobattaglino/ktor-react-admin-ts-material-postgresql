package jdbcat.ktor.example.db.dao

import jdbcat.core.*
import jdbcat.ktor.example.EntityNotFoundException
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.db.model.Option
import jdbcat.ktor.example.db.model.Options
import mu.KotlinLogging
import javax.sql.DataSource

class OptionDao(private val dataSource: DataSource) {

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

    suspend fun add(option: Option) = dataSource.txRequired { connection ->
        val stmt = createOptionSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(Options.id)
                )
                .setColumns {
                    option.copyValuesTo(it)
                }
        logger.debug { "add(): $stmt" }
        try {
            stmt.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val optionId: Int = stmt.generatedKeys.singleRow { it[Options.id] }
        option.copy(id = optionId)
    }

    suspend fun update(option: Option) = dataSource.txRequired { connection ->
        val stmt = updateOptionSqlTemplate
                .prepareStatement(
                        connection = connection,
                        returningColumnsOnUpdate = listOf(
                                Options.id,
                                Options.name,
                                Options.dateCreated
                        )
                )
                .setColumns {
                    option.copyValuesTo(it)
                }
        logger.debug { "update(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException(
                    errorMessage = "Entity Option id=${option.id} " +
                            "was not found and cannot be updated"
            )
        }
        val dateCreated = stmt.generatedKeys.singleRow { it[Options.dateCreated] }
        option.copy(dateCreated = dateCreated)
    }

    suspend fun queryById(id: Int) = dataSource.txRequired { connection ->
        val stmt = selectByIdSqlTemplate
                .prepareStatement(connection)
                .setColumns {
                    it[Options.id] = id
                }
        logger.debug { "getById(): $stmt" }
        stmt.executeQuery()
                .singleRowOrNull { Option.extractFrom(it) }
                ?: throw EntityNotFoundException(errorMessage = "Option id=$id cannot be found")
    }

    suspend fun queryByType(type: String, filter: Filter?) = dataSource.txRequired { connection ->
        var stmt = selectByTypeSqlTemplate
                .prepareStatement(connection)
                .setColumns {
                    it[Options.type] = type
                }

        filter?.let { filter ->
            filter.id?.let { id ->
                stmt = selectByIdsSqlTemplate.prepareStatement(connection)
                stmt.setArray(1, connection.createArrayOf("INT", id.toTypedArray()))
            }
            logger.debug { "selectAll() by productId, userId, id paged: $stmt" }
        }

        logger.debug { "getByType(): $stmt" }
        stmt.executeQuery().asSequence().map {
            Option.extractFrom(it)
        }
    }

    suspend fun queryAll() = dataSource.txRequired { connection ->
        val stmt = selectAllSqlTemplate.prepareStatement(connection)
        logger.debug { "queryAll(): $stmt" }
        stmt.executeQuery().asSequence().map {
            Option.extractFrom(it)
        }
    }

    suspend fun countAll() = dataSource.txRequired { connection ->
        val stmt = countAll.prepareStatement(connection)
        logger.debug { "countAll(): $stmt" }
        stmt.executeQuery().singleRow { it[CounterResult.counter] }
    }

    /**
     * Delete option by code.
     */
    suspend fun deleteById(id: Int) = dataSource.txRequired { connection ->
        val stmt = deleteById
                .prepareStatement(connection)
                .setColumns {
                    it[Options.id] = id
                }
        logger.debug { "deleteById(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException("Option id=$id does not exist")
        }
    }

    companion object {

        private val createTableIfNotExistsSqlTemplate = sqlTemplate(Options) {
            """
            | CREATE TABLE IF NOT EXISTS $tableName (
            |   ${columns.sqlDefinitions},
            |   UNIQUE ( $name )
            | )
            """
        }

        private val dropTableIfExistsSqlTemplate = sqlTemplate(Options) {
            "DROP TABLE IF EXISTS $tableName"
        }

        private val createOptionSqlTemplate = sqlTemplate(Options) {
            "INSERT INTO $tableName (${(columns - id).sqlNames}) VALUES (${(columns - id).sqlValues})"
        }

        private val updateOptionSqlTemplate = sqlTemplate(Options) {
            """
            | UPDATE $tableName
            |   SET ${(columns - id - dateCreated).sqlAssignNamesToValues}
            |   WHERE $id = ${id.v}
            """
        }

        private val selectByIdSqlTemplate = sqlTemplate(Options) {
            "SELECT * FROM $tableName WHERE $id = ${id.v}"
        }

        private val selectByIdsSqlTemplate = sqlTemplate(Options) {
            """
            | SELECT *
            |   FROM $tableName 
            |   WHERE $id = ANY (?)
            |   ORDER BY $id DESC
            """
        }

        private val selectByTypeSqlTemplate = sqlTemplate(Options) {
            "SELECT * FROM $tableName WHERE $type = ${type.v}"
        }

        private val selectAllSqlTemplate = sqlTemplate(Options) {
            "SELECT * FROM $tableName ORDER BY $type"
        }

        private val deleteById = sqlTemplate(Options) {
            "DELETE FROM $tableName WHERE $id = ${id.v}"
        }

        private val countAll = sqlTemplate(CounterResult, Options) { cr, e ->
            "SELECT COUNT(*) AS ${cr.counter} FROM ${e.tableName}"
        }

        object CounterResult : EphemeralTable() {
            val counter = integer("counter").nonnull()
        }
    }
}
