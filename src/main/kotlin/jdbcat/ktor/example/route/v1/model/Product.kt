package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Product
import jdbcat.ktor.example.util.precioCapilla
import jdbcat.ktor.example.util.precioMayor
import jdbcat.ktor.example.util.precioMenor
import jdbcat.ktor.example.util.precioMl
import java.util.*

data class CreateProductRequest(
        val catId: Int,
        val name: String,
        val material: String,
        val colorId: Int,
        val idMl: Int?,
        val priceId: Int,
        val manufacturingCost: Int,
        val photoId: String?,
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
            photoId = photoId,
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
        val photoId: String?,
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
            photoId = photoId,
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
        val precioMayor: Int,
        val precioCapilla: Int,
        val precioMenor: Int,
        val precioMl: Int,
        val photoId: String?,
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
                        precioMayor = precioMayor(entity.manufacturingCost),
                        precioCapilla = precioCapilla(entity.manufacturingCost),
                        precioMenor = precioMenor(entity.manufacturingCost),
                        precioMl = precioMl(entity.manufacturingCost),
                        photoId = entity.photoId,
                        notes = entity.notes,
                        dateCreated = entity.dateCreated
                )
    }
}
