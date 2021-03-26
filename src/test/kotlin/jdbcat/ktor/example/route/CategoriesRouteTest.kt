package jdbcat.ktor.example.route

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import jdbcat.core.tx
import jdbcat.ktor.example.AppSpek
import jdbcat.ktor.example.db.dao.CategoryDao
import jdbcat.ktor.example.db.model.Category
import jdbcat.ktor.example.route.v1.model.CategoryResponse
import org.amshove.kluent.`should equal`
import org.koin.ktor.ext.inject
import org.spekframework.spek2.style.specification.describe
import java.util.Date
import javax.sql.DataSource

// TODO write tests like Employees for Categories
// TODO runing test job, check this error: Caused by: java.lang.IllegalStateException: Could not find a valid Docker environment. Please see logs and check configuration
object CategoriesRouteTest : AppSpek({

    describe("Get all Categories - GET /api/v1/categories") {
        context("when two Categories exist in database") {
            it("should return HTTP 200 OK with two Category entities") {
                withApp {
                    val categoryDao by application.inject<CategoryDao>()
                    val dataSource by application.inject<DataSource>()
                    dataSource.tx {
                        categoryDao.add(newCategory("Cat 1"))
                        categoryDao.add(newCategory("Cat 2"))
                    }
                    handleRequest(HttpMethod.Get, "/api/v1/categories") {
                        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    }.apply {
                        response.status() `should equal` HttpStatusCode.OK
                        val list =
                            jacksonMapper.readValue<List<CategoryResponse>>(response.content!!)
                        list.size `should equal` 2
                        // val response1 = list.find { it.id == employee1.id }!!
                        // response1 sameAs employee1
                        // val response2 = list.find { it.id == employee2.id }!!
                        // response2 sameAs employee2
                    }
                }
            }
        }
    }
})

fun newCategory(name: String) = Category(
    id = null,
    name = name,
    dateCreated = Date()
)
