package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Product
import java.util.Date

data class CreateProductRequest(
    val name: String
) {
    // Here we set up the object to be saved
    fun toEntity() = Product(
        id = null,
        name = name,
        dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditProductRequest(
    val name: String
) {
    fun toEntity(id: Int) = Product(
        id = id,
        name = name,
        dateCreated = Date()
    )
}

data class ProductResponse(
    val id: Int,
    val name: String,
    val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Product) =
            ProductResponse(
                id = entity.id!!,
                name = entity.name,
                dateCreated = entity.dateCreated!!
            )
    }
}
