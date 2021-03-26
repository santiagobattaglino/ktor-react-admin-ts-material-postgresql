package jdbcat.ktor.example.route.v1

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jdbcat.core.tx
import jdbcat.ktor.example.db.dao.SaleDao
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.route.v1.model.CreateSaleRequest
import jdbcat.ktor.example.route.v1.model.EditSaleRequest
import jdbcat.ktor.example.route.v1.model.SaleResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.saleRoute() {

    val dataSource by inject<DataSource>()
    val saleDao by inject<SaleDao>()

    route("/sales") {

        // get all
        get("/") { _ ->
            val mapper = jacksonObjectMapper()
            val filter = call.parameters["filter"]?.let {
                mapper.readValue<Filter>(it)
            }
            val range = call.parameters["range"]?.let {
                mapper.readValue<List<Int>>(it)
            }
            val sort = call.parameters["sort"]?.let {
                mapper.readValue<List<String>>(it)
            }
            dataSource.tx { _ ->
                val response = saleDao
                        .selectAll(range = range, sort = sort)
                        .map { SaleResponse.fromEntity(it) }
                        .toList()
                call.response.header("X-Total-Count", saleDao.countAll())
                call.respond(response)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val response = saleDao
                        .select(id = id)
                        .let { SaleResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // post add
        post("/") {
            val addRequest = call.receive<CreateSaleRequest>()
            val itemToAdd = addRequest.toEntity()
            dataSource.tx {
                val response = saleDao
                        .insert(item = itemToAdd)
                        .let { SaleResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val editRequest = call.receive<EditSaleRequest>()
            val itemToUpdate = editRequest.toEntity(id = id)
            dataSource.tx {
                val response = saleDao
                        .update(item = itemToUpdate)
                        .let { SaleResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                saleDao.delete(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
