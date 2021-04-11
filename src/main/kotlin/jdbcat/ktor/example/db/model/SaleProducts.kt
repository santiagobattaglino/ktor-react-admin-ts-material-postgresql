package jdbcat.ktor.example.db.model

import jdbcat.core.*
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.*

object SaleProducts : Table(tableName = "sale_products") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val saleId = integer("sale_id").nonnull()
    val productId = integer("product_id").nonnull()
    val size = integer("size").nonnull()
    val quantity = integer("quantity").nonnull()
    val customPrice = integer("custom_price")
    val notes = varchar("notes", size = 255)
    val dateCreated = javaDate("date_created").nonnull()
}

data class SaleProduct(
        val id: Int? = null,
        val saleId: Int,
        val productId: Int,
        val size: Int,
        val quantity: Int,
        val customPrice: Int?,
        val notes: String?,
        val dateCreated: Date
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[SaleProducts.id] = id
        }
        builder[SaleProducts.saleId] = saleId
        builder[SaleProducts.productId] = productId
        builder[SaleProducts.size] = size
        builder[SaleProducts.quantity] = quantity
        builder[SaleProducts.customPrice] = customPrice
        builder[SaleProducts.notes] = notes
        builder[SaleProducts.dateCreated] = dateCreated
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = SaleProduct(
                id = extractor[SaleProducts.id],
                saleId = extractor[SaleProducts.saleId],
                productId = extractor[SaleProducts.productId],
                size = extractor[SaleProducts.size],
                quantity = extractor[SaleProducts.quantity],
                customPrice = extractor[SaleProducts.customPrice],
                notes = extractor[SaleProducts.notes],
                dateCreated = extractor[SaleProducts.dateCreated]
        )
    }
}
