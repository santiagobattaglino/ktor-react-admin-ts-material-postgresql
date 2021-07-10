package jdbcat.ktor.example.route.v1

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jdbcat.core.tx
import jdbcat.ktor.example.db.dao.ProductDao
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.route.v1.model.CreateProductRequest
import jdbcat.ktor.example.route.v1.model.EditProductRequest
import jdbcat.ktor.example.route.v1.model.ProductResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.productRoute() {

    val dataSource by this.inject<DataSource>()
    val dao by this.inject<ProductDao>()

    route("/products") {

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
                val productsResponse = dao
                        .selectAll(filter = filter, range = range, sort = sort)
                        .map { ProductResponse.fromEntity(it) }
                        .toList()
                call.response.header("X-Total-Count", dao.countAll())
                call.respond(productsResponse)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val productResponse = dao
                        .select(id = id)
                        .let { ProductResponse.fromEntity(it) }
                call.respond(productResponse)
            }
        }

        // post add
        post("/") {
            val addProductRequest = call.receive<CreateProductRequest>()
            val productToAdd = addProductRequest.toEntity()
            dataSource.tx {
                val productResponse = dao
                        .insert(item = productToAdd)
                        .let { ProductResponse.fromEntity(it) }
                call.respond(productResponse)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val editProductRequest = call.receive<EditProductRequest>()
            val productToUpdate = editProductRequest.toEntity(id = id)
            dataSource.tx {
                val productResponse = dao
                        .update(item = productToUpdate)
                        .let { ProductResponse.fromEntity(it) }
                call.respond(productResponse)
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
