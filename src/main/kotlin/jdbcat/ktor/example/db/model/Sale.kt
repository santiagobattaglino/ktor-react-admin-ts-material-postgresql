package jdbcat.ktor.example.db.model

import jdbcat.core.*
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.*

object Sales : Table(tableName = "sales") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val sellerId = integer("seller_id").nonnull()
    val clientId = integer("client_id").nonnull()
    val notes = varchar("notes", size = 255)
    val dateCreated = javaDate("date_created").nonnull()
}

data class Sale(
        val id: Int? = null,
        val sellerId: Int,
        val clientId: Int,
        val notes: String?,
        val dateCreated: Date
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Sales.id] = id
        }
        builder[Sales.sellerId] = sellerId
        builder[Sales.clientId] = clientId
        builder[Sales.notes] = notes
        builder[Sales.dateCreated] = dateCreated
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Sale(
                id = extractor[Sales.id],
                sellerId = extractor[Sales.sellerId],
                clientId = extractor[Sales.clientId],
                notes = extractor[Sales.notes],
                dateCreated = extractor[Sales.dateCreated]
        )
    }
}
