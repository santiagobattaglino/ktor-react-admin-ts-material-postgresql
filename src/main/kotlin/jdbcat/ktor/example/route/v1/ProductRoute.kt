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
import jdbcat.ktor.example.db.dao.ProductDao
import jdbcat.ktor.example.route.v1.model.CreateProductRequest
import jdbcat.ktor.example.route.v1.model.EditProductRequest
import jdbcat.ktor.example.route.v1.model.ProductResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.productRoute() {

    val dataSource by inject<DataSource>()
    val productDao by inject<ProductDao>()

    route("/products") {

        // get all
        get("/") { _ ->
            dataSource.tx { _ ->
                val productsResponse = productDao
                    .selectAll()
                    .map { ProductResponse.fromEntity(it) }
                    .toList()
                call.response.header("X-Total-Count", productsResponse.size)
                call.respond(productsResponse)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val productResponse = productDao
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
                val productResponse = productDao
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
                val productResponse = productDao
                    .update(item = productToUpdate)
                    .let { ProductResponse.fromEntity(it) }
                call.respond(productResponse)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                productDao.delete(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
