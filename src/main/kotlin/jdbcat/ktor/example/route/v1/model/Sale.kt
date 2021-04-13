package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Sale
import jdbcat.ktor.example.util.getPriceById
import java.util.*

data class CreateSaleRequest(
        val sellerId: Int,
        val clientId: Int,
        val paymentMethodId: Int,
        val priceId: Int,
        val notes: String?
) {
    fun toEntity() = Sale(
            id = null,
            sellerId = sellerId,
            clientId = clientId,
            paymentMethodId = paymentMethodId,
            priceId = priceId,
            notes = notes,
            dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditSaleRequest(
        val sellerId: Int,
        val paymentMethodId: Int,
        val priceId: Int,
        val clientId: Int,
        val notes: String?
) {
    fun toEntity(id: Int) = Sale(
            id = id,
            sellerId = sellerId,
            clientId = clientId,
            paymentMethodId = paymentMethodId,
            priceId = priceId,
            notes = notes,
            dateCreated = Date()
    )
}

data class SaleResponse(
        val id: Int,
        val sellerId: Int,
        val clientId: Int,
        val paymentMethodId: Int,
        val priceId: Int,
        val notes: String?,
        val dateCreated: Date,
        val total: Int?
) {
    companion object {
        fun fromEntity(entity: Sale) =
                SaleResponse(
                        id = entity.id!!,
                        sellerId = entity.sellerId,
                        clientId = entity.clientId,
                        paymentMethodId = entity.paymentMethodId,
                        priceId = entity.priceId,
                        total = entity.total?.let { getPriceById(it, entity.priceId) },
                        notes = entity.notes,
                        dateCreated = entity.dateCreated
                )
    }
}
