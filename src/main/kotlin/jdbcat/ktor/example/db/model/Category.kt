package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.Date

object Categories : Table(tableName = "categories") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val name = varchar("name", size = 100, specifier = "UNIQUE").nonnull()
    val dateCreated = javaDate("date_created").nonnull()
}

data class Category(
    val id: Int? = null,
    val name: String,
    val dateCreated: Date?
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Categories.id] = id
        }
        builder[Categories.name] = name
        if (dateCreated != null) {
            builder[Categories.dateCreated] = dateCreated
        }
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Category(
            id = extractor[Categories.id],
            name = extractor[Categories.name],
            dateCreated = extractor[Categories.dateCreated]
        )
    }
}
