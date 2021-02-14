package jdbcat.ktor.example.route.v1.model

import jdbcat.ktor.example.db.model.User
import java.util.Date

data class CreateUserRequest(
    val role: Role,
    val firstName: String,
    val lastName: String?,
    val address: String?,
    val phone: String?,
    val email: String?,
    val link: String?,
    val notes: String?
) {
    // Here we set up the object to be saved
    fun toEntity() = User(
        id = null,
        role = role,
        firstName = firstName,
        lastName = lastName,
        address = address,
        phone = phone,
        email = email,
        link = link,
        notes = notes,
        dateCreated = Date()
    )
}

data class EditUserRequest(
    val id: Int,
    val role: Role,
    val firstName: String,
    val lastName: String?,
    val address: String?,
    val phone: String?,
    val email: String?,
    val link: String?,
    val notes: String?,
    val dateCreated: Date?
) {
    fun toEntity(id: Int) = User(
        id = id,
        role = role,
        firstName = firstName,
        lastName = lastName,
        address = address,
        phone = phone,
        email = email,
        link = link,
        notes = notes,
        dateCreated = Date()
    )
}

data class UserResponse(
    val id: Int,
    val role: Role,
    val firstName: String,
    val lastName: String?,
    val address: String?,
    val phone: String?,
    val email: String?,
    val link: String?,
    val notes: String?,
    val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: User) =
            UserResponse(
                id = entity.id!!,
                role = entity.role,
                firstName = entity.firstName,
                lastName = entity.lastName,
                address = entity.address,
                phone = entity.phone,
                email = entity.email,
                link = entity.link,
                notes = entity.notes,
                dateCreated = entity.dateCreated!!
            )
    }
}
