package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Product
import java.util.Date

data class CreateProductRequest(
    val catId: Int,
    val name: String,
    val material: String,
    val color: String,
    val idMl: Int?,
    val priceId: Int,
    val notes: String?
) {
    // Here we set up the object to be saved
    fun toEntity() = Product(
        id = null,
        catId = catId,
        name = name,
        material = material,
        color = color,
        idMl = idMl,
        priceId = priceId,
        notes = notes,
        dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditProductRequest(
    val catId: Int,
    val name: String,
    val material: String,
    val color: String,
    val idMl: Int?,
    val priceId: Int,
    val notes: String?
) {
    fun toEntity(id: Int) = Product(
        id = id,
        catId = catId,
        name = name,
        material = material,
        color = color,
        idMl = idMl,
        priceId = priceId,
        notes = notes,
        dateCreated = Date()
    )
}

data class ProductResponse(
    val id: Int,
    val catId: Int,
    val name: String,
    val material: String,
    val color: String,
    val idMl: Int?,
    val priceId: Int,
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
                color = entity.color,
                idMl = entity.idMl,
                priceId = entity.priceId,
                notes = entity.notes,
                dateCreated = entity.dateCreated
            )
    }
}
