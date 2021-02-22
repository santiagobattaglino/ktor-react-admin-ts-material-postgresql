package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.Date

object Colors : Table(tableName = "colors") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val name = varchar("name", size = 100, specifier = "UNIQUE").nonnull()
    val dateCreated = javaDate("date_created").nonnull()
}

data class Color(
    val id: Int? = null,
    val name: String,
    val dateCreated: Date?
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Colors.id] = id
        }
        builder[Colors.name] = name
        if (dateCreated != null) {
            builder[Colors.dateCreated] = dateCreated
        }
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Color(
            id = extractor[Colors.id],
            name = extractor[Colors.name],
            dateCreated = extractor[Colors.dateCreated]
        )
    }
}
