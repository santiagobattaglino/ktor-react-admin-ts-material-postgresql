package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Sale
import java.util.*

data class CreateSaleRequest(
        val placeId: Int,
        val userId: Int,
        val notes: String?
) {
    fun toEntity() = Sale(
            id = null,
            placeId = placeId,
            userId = userId,
            notes = notes,
            dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditSaleRequest(
        val placeId: Int,
        val userId: Int,
        val notes: String?
) {
    fun toEntity(id: Int) = Sale(
            id = id,
            placeId = placeId,
            userId = userId,
            notes = notes,
            dateCreated = Date()
    )
}

data class SaleResponse(
        val id: Int,
        val placeId: Int,
        val userId: Int,
        val notes: String?,
        val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Sale) =
                SaleResponse(
                        id = entity.id!!,
                        placeId = entity.placeId,
                        userId = entity.userId,
                        notes = entity.notes,
                        dateCreated = entity.dateCreated
                )
    }
}