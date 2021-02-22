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
import jdbcat.ktor.example.db.dao.CategoryDao
import jdbcat.ktor.example.route.v1.model.CategoryResponse
import jdbcat.ktor.example.route.v1.model.CreateCategoryRequest
import jdbcat.ktor.example.route.v1.model.EditCategoryRequest
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.categoryRoute() {

    val dataSource by inject<DataSource>()
    val categoryDao by inject<CategoryDao>()

    route("/categories") {

        // get all
        get("/") { _ ->
            dataSource.tx { _ ->
                val categoriesResponse = categoryDao
                    .queryAll()
                    .map { CategoryResponse.fromEntity(it) }
                    .toList()
                call.response.header("X-Total-Count", categoriesResponse.size)
                call.respond(categoriesResponse)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val categoryResponse = categoryDao
                    .queryById(id = id)
                    .let { CategoryResponse.fromEntity(it) }
                call.respond(categoryResponse)
            }
        }

        // post add
        post("/") {
            val addCategoryRequest = call.receive<CreateCategoryRequest>()
            val categoryToAdd = addCategoryRequest.toEntity()
            dataSource.tx {
                val categoryResponse = categoryDao
                    .add(category = categoryToAdd)
                    .let { CategoryResponse.fromEntity(it) }
                call.respond(categoryResponse)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val editCategoryRequest = call.receive<EditCategoryRequest>()
            val categoryToUpdate = editCategoryRequest.toEntity(id = id)
            dataSource.tx {
                val categoryResponse = categoryDao
                    .update(category = categoryToUpdate)
                    .let { CategoryResponse.fromEntity(it) }
                call.respond(categoryResponse)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                categoryDao.deleteById(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
