package jdbcat.ktor.example.db.model

import jdbcat.core.*
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.*

object Sales : Table(tableName = "sales") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val placeId = integer("place_id").nonnull()
    val userId = integer("user_id").nonnull()
    val notes = varchar("notes", size = 255)
    val dateCreated = javaDate("date_created").nonnull()
}

data class Sale(
        val id: Int? = null,
        val placeId: Int,
        val userId: Int,
        val notes: String?,
        val dateCreated: Date
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Sales.id] = id
        }
        builder[Sales.placeId] = placeId
        builder[Sales.userId] = userId
        builder[Sales.notes] = notes
        builder[Sales.dateCreated] = dateCreated
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Sale(
                id = extractor[Sales.id],
                placeId = extractor[Sales.placeId],
                userId = extractor[Sales.userId],
                notes = extractor[Sales.notes],
                dateCreated = extractor[Sales.dateCreated]
        )
    }
}