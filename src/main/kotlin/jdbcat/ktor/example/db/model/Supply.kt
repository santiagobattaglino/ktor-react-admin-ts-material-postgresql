package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.integer
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.Date

object Supplies : Table(tableName = "supplies") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val userId = integer("user_id").nonnull()
    val name = varchar("name", size = 100, specifier = "UNIQUE").nonnull()
    val price = varchar("price", size = 10, specifier = "UNIQUE").nonnull()
    val dateCreated = javaDate("date_created").nonnull()
}

data class Supply(
    val id: Int? = null,
    val userId: Int,
    val name: String,
    val price: String,
    val dateCreated: Date?
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Supplies.id] = id
        }
        builder[Supplies.userId] = userId
        builder[Supplies.name] = name
        builder[Supplies.price] = price
        if (dateCreated != null) {
            builder[Supplies.dateCreated] = dateCreated
        }
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Supply(
            id = extractor[Supplies.id],
            userId = extractor[Supplies.userId],
            name = extractor[Supplies.name],
            price = extractor[Supplies.price],
            dateCreated = extractor[Supplies.dateCreated]
        )
    }
}
