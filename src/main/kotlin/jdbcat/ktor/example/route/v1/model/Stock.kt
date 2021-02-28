package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Stock
import jdbcat.ktor.example.db.model.StockByUser
import java.util.Date

data class CreateStockRequest(
    val productId: Int,
    val size: Int,
    val quantity: Int,
    val userId: Int,
    val notes: String?
) {
    // Here we set up the object to be saved
    fun toEntity() = Stock(
        id = null,
        productId = productId,
        size = size,
        quantity = quantity,
        userId = userId,
        notes = notes,
        dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditStockRequest(
    val productId: Int,
    val size: Int,
    val quantity: Int,
    val userId: Int,
    val notes: String?
) {
    fun toEntity(id: Int) = Stock(
        id = id,
        productId = productId,
        size = size,
        quantity = quantity,
        userId = userId,
        notes = notes,
        dateCreated = Date()
    )
}

data class StockResponse(
    val id: Int,
    val productId: Int,
    val size: Int,
    val quantity: Int,
    val userId: Int,
    val notes: String?,
    val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Stock) =
            StockResponse(
                id = entity.id!!,
                productId = entity.productId,
                size = entity.size,
                quantity = entity.quantity,
                userId = entity.userId,
                notes = entity.notes,
                dateCreated = entity.dateCreated
            )
    }
}

data class StockUserResponse(
    val productId: Int,
    val quantity: Int
) {
    companion object {
        fun fromEntity(entity: StockByUser) =
            StockUserResponse(
                productId = entity.productId,
                quantity = entity.quantity
            )
    }
}
