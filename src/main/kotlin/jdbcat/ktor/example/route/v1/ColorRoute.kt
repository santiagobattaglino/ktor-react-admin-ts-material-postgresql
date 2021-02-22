package jdbcat.ktor.example.route.v1

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import jdbcat.core.tx
import jdbcat.ktor.example.db.dao.ColorDao
import jdbcat.ktor.example.route.v1.model.ColorResponse
import jdbcat.ktor.example.route.v1.model.CreateColorRequest
import jdbcat.ktor.example.route.v1.model.EditColorRequest
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.colorRoute() {

    val dataSource by inject<DataSource>()
    val colorDao by inject<ColorDao>()

    route("/colors") {

        // get all
        get("/") { _ ->
            dataSource.tx { _ ->
                val response = colorDao
                    .selectAll()
                    .map { ColorResponse.fromEntity(it) }
                    .toList()
                call.response.header("X-Total-Count", response.size)
                call.respond(response)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val colorResponse = colorDao
                    .select(id = id)
                    .let { ColorResponse.fromEntity(it) }
                call.respond(colorResponse)
            }
        }

        // post add
        post("/") {
            val addColorRequest = call.receive<CreateColorRequest>()
            val colorToAdd = addColorRequest.toEntity()
            dataSource.tx {
                val colorResponse = colorDao
                    .insert(colorToAdd)
                    .let { ColorResponse.fromEntity(it) }
                call.respond(colorResponse)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val editColorRequest = call.receive<EditColorRequest>()
            val colorToUpdate = editColorRequest.toEntity(id = id)
            dataSource.tx {
                val colorResponse = colorDao
                    .update(colorToUpdate)
                    .let { ColorResponse.fromEntity(it) }
                call.respond(colorResponse)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                colorDao.delete(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
