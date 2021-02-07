package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Category
import java.util.Date

data class CreateCategoryRequest(
    val name: String
) {
    // Here we set up the object to be saved
    fun toEntity() = Category(
        id = null,
        name = name,
        dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditCategoryRequest(
    val name: String
) {
    fun toEntity(id: Int) = Category(
        id = id,
        name = name,
        dateCreated = Date()
    )
}

data class CategoryResponse(
    val id: Int,
    val name: String,
    val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Category) =
            CategoryResponse(
                id = entity.id!!,
                name = entity.name,
                dateCreated = entity.dateCreated!!
            )
    }
}
