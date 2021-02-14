package jdbcat.ktor.example.route.v1

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import jdbcat.core.tx
import jdbcat.ktor.example.db.dao.UserDao
import jdbcat.ktor.example.route.v1.model.CreateUserRequest
import jdbcat.ktor.example.route.v1.model.EditUserRequest
import jdbcat.ktor.example.route.v1.model.UserResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.userRoute() {

    val ds by inject<DataSource>()
    val dao by inject<UserDao>()

    route("/users") {

        // get all
        get("/") { _ ->
            ds.tx { _ ->
                val response = dao
                    .selectAll()
                    .map { UserResponse.fromEntity(it) }
                    .toList()
                call.respond(response)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            ds.tx {
                val response = dao
                    .select(id = id)
                    .let { UserResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // post insert
        post("/") {
            val request = call.receive<CreateUserRequest>()
            val itemToInsert = request.toEntity()
            ds.tx {
                val response = dao
                    .insert(item = itemToInsert)
                    .let { UserResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val request = call.receive<EditUserRequest>()
            val itemToUpdate = request.toEntity(id = id)
            ds.tx {
                val response = dao
                    .update(item = itemToUpdate)
                    .let { UserResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            ds.tx {
                dao.delete(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
