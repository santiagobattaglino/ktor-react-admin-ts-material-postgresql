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
import jdbcat.dialects.pg.pgAssignNamesToExcludedNames
import jdbcat.ktor.example.EntityNotFoundException
import jdbcat.ktor.example.db.model.Categories
import jdbcat.ktor.example.db.model.Category
import mu.KotlinLogging
import javax.sql.DataSource

class CategoryDao(private val dataSource: DataSource) {

    private val logger = KotlinLogging.logger { }

    // txRequired enforces usage of transaction that needs to be started by caller
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

    suspend fun add(category: Category) = dataSource.txRequired { connection ->
        val stmt = createCategorySqlTemplate
            .prepareStatement(
                connection = connection,
                returningColumnsOnUpdate = listOf(Categories.id)
            )
            .setColumns {
                category.copyValuesTo(it)
            }
        logger.debug { "add(): $stmt" }
        try {
            stmt.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val categoryId: Int = stmt.generatedKeys.singleRow { it[Categories.id] }
        category.copy(id = categoryId)
    }

    suspend fun update(category: Category) = dataSource.txRequired { connection ->
        val stmt = updateCategorySqlTemplate
            .prepareStatement(
                connection = connection,
                returningColumnsOnUpdate = listOf(
                    Categories.id,
                    Categories.name,
                    Categories.dateCreated
                )
            )
            .setColumns {
                category.copyValuesTo(it)
            }
        logger.debug { "update(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException(
                errorMessage = "Entity Category id=${category.id} " +
                    "was not found and cannot be updated"
            )
        }
        val dateCreated = stmt.generatedKeys.singleRow { it[Categories.dateCreated] }
        category.copy(dateCreated = dateCreated)
    }

    suspend fun queryById(id: Int) = dataSource.txRequired { connection ->
        val stmt = selectByIdSqlTemplate
            .prepareStatement(connection)
            .setColumns {
                it[Categories.id] = id
            }
        logger.debug { "getById(): $stmt" }
        stmt.executeQuery()
            .singleRowOrNull { Category.extractFrom(it) }
            ?: throw EntityNotFoundException(errorMessage = "Category id=$id cannot be found")
    }

    suspend fun queryAll() = dataSource.txRequired { connection ->
        val stmt = selectAllOrderedByNameSqlTemplate.prepareStatement(connection)
        logger.debug { "queryAll(): $stmt" }
        stmt.executeQuery().asSequence().map {
            Category.extractFrom(it)
        }
    }

    suspend fun countAll() = dataSource.txRequired { connection ->
        val stmt = countAll.prepareStatement(connection)
        logger.debug { "countAll(): $stmt" }
        stmt.executeQuery().singleRow { it[CounterResult.counter] }
    }

    /**
     * Delete category by code.
     */
    suspend fun deleteById(id: Int) = dataSource.txRequired { connection ->
        val stmt = deleteById
            .prepareStatement(connection)
            .setColumns {
                it[Categories.id] = id
            }
        logger.debug { "deleteById(): $stmt" }
        if (stmt.executeUpdate() == 0) {
            throw EntityNotFoundException("Category id=$id does not exist")
        }
    }

    companion object {

        private val createTableIfNotExistsSqlTemplate = sqlTemplate(Categories) {
            """
            | CREATE TABLE IF NOT EXISTS $tableName (
            |   ${columns.sqlDefinitions},
            |   UNIQUE ( $name )
            | )
            """
        }

        private val dropTableIfExistsSqlTemplate = sqlTemplate(Categories) {
            "DROP TABLE IF EXISTS $tableName"
        }

        // * Upsert (Update or Insert) functionality: https://www.postgresql.org/docs/9.5/static/sql-insert.html
        // * We don't want to update dateCreated field, it needs to be updated only once - when record is added
        private val upsertCategorySqlTemplate = sqlTemplate(Categories) {
            """
            | INSERT INTO $tableName (${columns.sqlNames}) VALUES (${columns.sqlValues})
            | ON CONFLICT ($id)
            | DO UPDATE SET ${(columns - dateCreated).pgAssignNamesToExcludedNames}
            """
        }

        private val createCategorySqlTemplate = sqlTemplate(Categories) {
            "INSERT INTO $tableName (${(columns - id).sqlNames}) VALUES (${(columns - id).sqlValues})"
        }

        private val updateCategorySqlTemplate = sqlTemplate(Categories) {
            // .sqlAssignNamesToValues property represents a string with comma-separated column name
            // to value assignments such as: "code = ..., firstName = ..., ..."
            // since we don't want to assign any values to "id" and "dateCreated" fields (there
            // were assigned during creation of row) - we exclude them from a list.
            """
            | UPDATE $tableName
            |   SET ${(columns - id - dateCreated).sqlAssignNamesToValues}
            |   WHERE $id = ${id.v}
            """
        }

        private val selectByIdSqlTemplate = sqlTemplate(Categories) {
            "SELECT * FROM $tableName WHERE $id = ${id.v}"
        }

        private val selectAllOrderedByNameSqlTemplate = sqlTemplate(Categories) {
            "SELECT * FROM $tableName ORDER BY $name"
        }

        private val deleteById = sqlTemplate(Categories) {
            "DELETE FROM $tableName WHERE $id = ${id.v}"
        }

        private val countAll = sqlTemplate(CounterResult, Categories) { cr, e ->
            "SELECT COUNT(*) AS ${cr.counter} FROM ${e.tableName}"
        }

        object CounterResult : EphemeralTable() {
            val counter = integer("counter").nonnull()
        }
    }
}
