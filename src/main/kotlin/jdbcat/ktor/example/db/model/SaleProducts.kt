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

object PriceFields : EphemeralTable() {
    val paymentMethodId = integer("payment_method_id").nonnull()
    val priceId = integer("price_id").nonnull()
    val manufacturingCost = integer("manufacturing_cost").nonnull()
    val manufacturingCostTotal = integer("manufacturing_cost_total").nonnull()
}

data class SaleProduct(
        val id: Int? = null,
        val saleId: Int,
        val productId: Int,
        val size: Int,
        val quantity: Int,
        val customPrice: Int?,
        val notes: String?,
        val dateCreated: Date,
        val paymentMethodId: Int? = null,
        val priceId: Int? = null,
        val manufacturingCost: Int? = null,
        val manufacturingCostTotal: Int? = null
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
        if (paymentMethodId != null)
            builder[PriceFields.paymentMethodId] = paymentMethodId
        if (priceId != null)
            builder[PriceFields.priceId] = priceId
        if (manufacturingCost != null)
            builder[PriceFields.manufacturingCost] = manufacturingCost
        if (manufacturingCostTotal != null)
            builder[PriceFields.manufacturingCostTotal] = manufacturingCostTotal
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
                dateCreated = extractor[SaleProducts.dateCreated],
                paymentMethodId = extractor[PriceFields.paymentMethodId],
                priceId = extractor[PriceFields.priceId],
                manufacturingCost = extractor[PriceFields.manufacturingCost],
                manufacturingCostTotal = extractor[PriceFields.manufacturingCostTotal]
        )
    }
}
