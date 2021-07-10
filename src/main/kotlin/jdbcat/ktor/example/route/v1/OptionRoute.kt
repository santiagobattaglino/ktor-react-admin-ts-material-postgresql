package jdbcat.ktor.example.route

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jdbcat.core.tx
import jdbcat.ktor.example.db.dao.OptionDao
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.route.v1.adminRoute
import jdbcat.ktor.example.route.v1.model.CreateOptionRequest
import jdbcat.ktor.example.route.v1.model.EditOptionRequest
import jdbcat.ktor.example.route.v1.model.OptionResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.optionRoute() {

    val dataSource by this.inject<DataSource>()
    val dao by this.inject<OptionDao>()

    route("/options") {

        // get all
        get("/") { _ ->
            dataSource.tx { _ ->
                val response = dao
                        .queryAll()
                        .map { OptionResponse.fromEntity(it) }
                        .toList()
                call.response.header("X-Total-Count", response.size)
                call.respond(response)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val response = dao
                        .queryById(id = id)
                        .let { OptionResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // get by type
        get("/type/{type}") { _ ->
            val type = call.parameters["type"]!!
            val mapper = jacksonObjectMapper()
            val filter = call.parameters["filter"]?.let {
                mapper.readValue<Filter>(it)
            }

            dataSource.tx {
                val response = dao
                        .queryByType(type = type, filter = filter)
                        .map { OptionResponse.fromEntity(it) }
                        .toList()
                call.response.header("X-Total-Count", response.size)
                call.respond(response)
            }
        }

        // post add
        post("/") {
            val addRequest = call.receive<CreateOptionRequest>()
            val optionToAdd = addRequest.toEntity()
            dataSource.tx {
                val response = dao
                        .add(option = optionToAdd)
                        .let { OptionResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val editRequest = call.receive<EditOptionRequest>()
            val optionToUpdate = editRequest.toEntity(id = id)
            dataSource.tx {
                val response = dao
                        .update(option = optionToUpdate)
                        .let { OptionResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                dao.deleteById(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
