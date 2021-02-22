package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Color
import java.util.Date

data class CreateColorRequest(
    val name: String
) {
    // Here we set up the object to be saved
    fun toEntity() = Color(
        id = null,
        name = name,
        dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditColorRequest(
    val name: String
) {
    fun toEntity(id: Int) = Color(
        id = id,
        name = name,
        dateCreated = Date()
    )
}

data class ColorResponse(
    val id: Int,
    val name: String,
    val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Color) =
            ColorResponse(
                id = entity.id!!,
                name = entity.name.capitalize(),
                dateCreated = entity.dateCreated!!
            )
    }
}
