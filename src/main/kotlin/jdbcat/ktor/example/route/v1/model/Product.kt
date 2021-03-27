package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Product
import java.util.*

data class CreateProductRequest(
        val catId: Int,
        val name: String,
        val material: String,
        val colorId: Int,
        val idMl: Int?,
        val priceId: Int,
        val manufacturingCost: Int,
        val notes: String?
) {
    // Here we set up the object to be saved
    fun toEntity() = Product(
            id = null,
            catId = catId,
            name = name,
            material = material,
            colorId = colorId,
            idMl = idMl,
            priceId = priceId,
            manufacturingCost = manufacturingCost,
            notes = notes,
            dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditProductRequest(
        val catId: Int,
        val name: String,
        val material: String,
        val colorId: Int,
        val idMl: Int?,
        val priceId: Int,
        val manufacturingCost: Int,
        val notes: String?
) {
    fun toEntity(id: Int) = Product(
            id = id,
            catId = catId,
            name = name,
            material = material,
            colorId = colorId,
            idMl = idMl,
            priceId = priceId,
            manufacturingCost = manufacturingCost,
            notes = notes,
            dateCreated = Date()
    )
}

data class ProductResponse(
        val id: Int,
        val catId: Int,
        val name: String,
        val material: String,
        val colorId: Int,
        val idMl: Int?,
        val priceId: Int,
        val manufacturingCost: Int,
        val notes: String?,
        val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Product) =
                ProductResponse(
                        id = entity.id!!,
                        catId = entity.catId,
                        name = entity.name,
                        material = entity.material,
                        colorId = entity.colorId,
                        idMl = entity.idMl,
                        priceId = entity.priceId,
                        manufacturingCost = entity.manufacturingCost,
                        notes = entity.notes,
                        dateCreated = entity.dateCreated
                )
    }
}
