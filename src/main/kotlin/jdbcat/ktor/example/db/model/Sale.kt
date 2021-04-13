package jdbcat.ktor.example.db.model

import jdbcat.core.*
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.*

object Sales : Table(tableName = "sales") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val sellerId = integer("seller_id").nonnull()
    val clientId = integer("client_id").nonnull()
    val paymentMethodId = integer("payment_method_id").nonnull()
    val priceId = integer("price_id").nonnull()
    val notes = varchar("notes", size = 255)
    val dateCreated = javaDate("date_created").nonnull()
}

object SaleAliasFields : EphemeralTable() {
    val total = integer("total").nonnull()
}

data class Sale(
        val id: Int? = null,
        val sellerId: Int,
        val clientId: Int,
        val paymentMethodId: Int,
        val priceId: Int,
        val notes: String?,
        val dateCreated: Date,
        val total: Int? = null
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null)
            builder[Sales.id] = id
        builder[Sales.sellerId] = sellerId
        builder[Sales.clientId] = clientId
        builder[Sales.paymentMethodId] = paymentMethodId
        builder[Sales.priceId] = priceId
        builder[Sales.notes] = notes
        builder[Sales.dateCreated] = dateCreated
        if (total != null)
            builder[SaleAliasFields.total] = total
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Sale(
                id = extractor[Sales.id],
                sellerId = extractor[Sales.sellerId],
                clientId = extractor[Sales.clientId],
                paymentMethodId = extractor[Sales.paymentMethodId],
                priceId = extractor[Sales.priceId],
                notes = extractor[Sales.notes],
                dateCreated = extractor[Sales.dateCreated],
                total = extractor[SaleAliasFields.total]
        )
    }
}
