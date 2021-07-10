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
import jdbcat.ktor.example.db.dao.PriceDao
import jdbcat.ktor.example.db.model.Price
import jdbcat.ktor.example.route.v1.model.CreatePriceRequest
import jdbcat.ktor.example.route.v1.model.EditPriceRequest
import jdbcat.ktor.example.route.v1.model.PriceResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.priceRoute() {

    val ds by this.inject<DataSource>()
    val dao by this.inject<PriceDao>()

    route("/prices") {

        // get all
        get("/") { _ ->
            ds.tx { _ ->
                val response = dao
                    .selectAll()
                    .map { PriceResponse.fromEntity(it, getManufacturingCost(it)) }
                    .toList()
                call.response.header("X-Total-Count", response.size)
                call.respond(response)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            ds.tx {
                val response = dao
                    .select(id = id)
                    .let { PriceResponse.fromEntity(it, getManufacturingCost(it)) }
                call.respond(response)
            }
        }

        // post insert
        post("/") {
            val request = call.receive<CreatePriceRequest>()
            val itemToInsert = request.toEntity()
            ds.tx {
                val response = dao
                    .insert(item = itemToInsert)
                    .let { PriceResponse.fromEntity(it, getManufacturingCost(it)) }
                call.respond(response)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val request = call.receive<EditPriceRequest>()
            val itemToUpdate = request.toEntity(id = id)
            ds.tx {
                val response = dao
                    .update(item = itemToUpdate)
                    .let { PriceResponse.fromEntity(it, getManufacturingCost(it)) }
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

fun getManufacturingCost(price: Price): Int {
    return price.manoDeObra * 2
}
