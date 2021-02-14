package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.enumByName
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.dialects.pg.pgText
import jdbcat.ext.javaDate
import jdbcat.ktor.example.route.v1.model.Role
import java.util.Date

object Users : Table(tableName = "users") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val role = enumByName<Role>("role", 20).nonnull()
    val firstName = varchar("first_name", size = 100).nonnull()
    val lastName = varchar("last_name", size = 100)
    val address = varchar("address", size = 100)
    val phone = varchar("phone", size = 100)
    val email = varchar("email", size = 100)
    val link = varchar("link", size = 255)
    val notes = pgText("notes")
    val dateCreated = javaDate("date_created").nonnull()
}

data class User(
    val id: Int? = null,
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
    fun copyValuesTo(builder: ColumnValueBuilder) {
        id?.let { builder[Users.id] = it }
        builder[Users.role] = role
        builder[Users.firstName] = firstName
        builder[Users.lastName] = lastName
        builder[Users.address] = address
        builder[Users.phone] = phone
        builder[Users.email] = email
        builder[Users.link] = link
        builder[Users.notes] = notes
        dateCreated?.let {
            builder[Users.dateCreated] = dateCreated
        }
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = User(
            id = extractor[Users.id],
            role = extractor[Users.role],
            firstName = extractor[Users.firstName],
            lastName = extractor[Users.lastName],
            address = extractor[Users.address],
            phone = extractor[Users.phone],
            email = extractor[Users.email],
            link = extractor[Users.link],
            notes = extractor[Users.notes],
            dateCreated = extractor[Users.dateCreated]
        )
    }
}
