package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.Date

object Products : Table(tableName = "products") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val name = varchar("name", size = 100, specifier = "UNIQUE").nonnull()
    val dateCreated = javaDate("date_created").nonnull()
}

data class Product(
    val id: Int? = null,
    val name: String,
    val dateCreated: Date?
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Products.id] = id
        }
        builder[Products.name] = name
        if (dateCreated != null) {
            builder[Products.dateCreated] = dateCreated
        }
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Product(
            id = extractor[Products.id],
            name = extractor[Products.name],
            dateCreated = extractor[Products.dateCreated]
        )
    }
}
