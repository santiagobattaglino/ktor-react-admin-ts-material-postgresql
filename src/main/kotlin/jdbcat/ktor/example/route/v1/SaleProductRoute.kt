package jdbcat.ktor.example.route.v1

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jdbcat.core.tx
import jdbcat.ktor.example.db.dao.SaleProductDao
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.route.v1.model.CreateSaleProductRequest
import jdbcat.ktor.example.route.v1.model.EditSaleProductRequest
import jdbcat.ktor.example.route.v1.model.SaleProductResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.saleProductRoute() {

    val dataSource by inject<DataSource>()
    val dao by inject<SaleProductDao>()

    route("/saleproducts") {

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
                val response = dao
                        .selectAll(filter = filter, range = range, sort = sort)
                        .map { SaleProductResponse.fromEntity(it) }
                        .toList()
                call.response.header("X-Total-Count", dao.countAll())
                call.respond(response)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val response = dao
                        .select(id = id)
                        .let { SaleProductResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // post add
        post("/") {
            val addRequest = call.receive<CreateSaleProductRequest>()
            val itemToAdd = addRequest.toEntity()
            dataSource.tx {
                val response = dao
                        .insert(item = itemToAdd)
                        .let { SaleProductResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val editRequest = call.receive<EditSaleProductRequest>()
            val itemToUpdate = editRequest.toEntity(id = id)
            dataSource.tx {
                val response = dao
                        .update(item = itemToUpdate)
                        .let { SaleProductResponse.fromEntity(it) }
                call.respond(response)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                dao.delete(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
