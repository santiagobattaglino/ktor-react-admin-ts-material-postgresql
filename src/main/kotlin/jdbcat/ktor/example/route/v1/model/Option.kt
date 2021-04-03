package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Option
import java.util.*

data class CreateOptionRequest(
        val name: String,
        val type: String
) {
    // Here we set up the object to be saved
    fun toEntity() = Option(
            id = null,
            name = name,
            type = type,
            dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditOptionRequest(
        val name: String,
        val type: String
) {
    fun toEntity(id: Int) = Option(
            id = id,
            name = name,
            type = type,
            dateCreated = Date()
    )
}

data class OptionResponse(
        val id: Int,
        val name: String,
        val type: String,
        val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Option) =
                OptionResponse(
                        id = entity.id!!,
                        name = entity.name.capitalize(),
                        type = entity.type,
                        dateCreated = entity.dateCreated!!
                )
    }
}
