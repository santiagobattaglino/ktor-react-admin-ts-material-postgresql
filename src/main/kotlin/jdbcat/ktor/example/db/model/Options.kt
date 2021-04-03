package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.*

object Options : Table(tableName = "options") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val name = varchar("name", size = 100, specifier = "UNIQUE").nonnull()
    val type = varchar("type", size = 100).nonnull()
    val dateCreated = javaDate("date_created").nonnull()
}

data class Option(
        val id: Int? = null,
        val name: String,
        val type: String,
        val dateCreated: Date?
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Options.id] = id
        }
        builder[Options.name] = name
        builder[Options.type] = type
        if (dateCreated != null) {
            builder[Options.dateCreated] = dateCreated
        }
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Option(
                id = extractor[Options.id],
                name = extractor[Options.name],
                type = extractor[Options.type],
                dateCreated = extractor[Options.dateCreated]
        )
    }
}
