package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.SaleProduct
import java.util.*

data class CreateSaleProductRequest(
        val saleId: Int,
        val productId: Int,
        val size: Int,
        val quantity: Int,
        val paymentMethodId: Int,
        val priceId: Int,
        val customPrice: Int?,
        val notes: String?
) {
    fun toEntity() = SaleProduct(
            id = null,
            saleId = saleId,
            productId = productId,
            size = size,
            quantity = quantity,
            paymentMethodId = paymentMethodId,
            priceId = priceId,
            customPrice = customPrice,
            notes = notes,
            dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditSaleProductRequest(
        val saleId: Int,
        val productId: Int,
        val size: Int,
        val quantity: Int,
        val paymentMethodId: Int,
        val priceId: Int,
        val customPrice: Int?,
        val notes: String?
) {
    fun toEntity(id: Int) = SaleProduct(
            id = id,
            saleId = saleId,
            productId = productId,
            size = size,
            quantity = quantity,
            paymentMethodId = paymentMethodId,
            priceId = priceId,
            customPrice = customPrice,
            notes = notes,
            dateCreated = Date()
    )
}

data class SaleProductResponse(
        val id: Int,
        val saleId: Int,
        val productId: Int,
        val size: Int,
        val quantity: Int,
        val paymentMethodId: Int,
        val priceId: Int,
        val customPrice: Int?,
        val notes: String?,
        val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: SaleProduct) =
                SaleProductResponse(
                        id = entity.id!!,
                        saleId = entity.saleId,
                        productId = entity.productId,
                        size = entity.size,
                        quantity = entity.quantity,
                        paymentMethodId = entity.paymentMethodId,
                        priceId = entity.priceId,
                        customPrice = entity.customPrice,
                        notes = entity.notes,
                        dateCreated = entity.dateCreated
                )
    }
}
