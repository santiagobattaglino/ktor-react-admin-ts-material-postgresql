package jdbcat.ktor.example.route

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import jdbcat.core.tx
import jdbcat.ktor.example.AppSpek
import jdbcat.ktor.example.db.dao.ProductDao
import jdbcat.ktor.example.db.model.Product
import jdbcat.ktor.example.route.v1.model.ProductResponse
import org.amshove.kluent.`should equal`
import org.koin.ktor.ext.inject
import org.spekframework.spek2.style.specification.describe
import java.util.Date
import javax.sql.DataSource

// TODO write tests like Employees for Products
// TODO runing test job, check this error: Caused by: java.lang.IllegalStateException: Could not find a valid Docker environment. Please see logs and check configuration
object ProductsRouteTest : AppSpek({

    describe("Get all Products - GET /api/v1/products") {
        context("when two Products exist in database") {
            it("should return HTTP 200 OK with two Product entities") {
                withApp {
                    val productDao by application.inject<ProductDao>()
                    val dataSource by application.inject<DataSource>()
                    dataSource.tx {
                        productDao.insert(newProduct("Prod 1"))
                        productDao.insert(newProduct("Prod 2"))
                    }
                    handleRequest(HttpMethod.Get, "/api/v1/products") {
                        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    }.apply {
                        response.status() `should equal` HttpStatusCode.OK
                        val list =
                            jacksonMapper.readValue<List<ProductResponse>>(response.content!!)
                        list.size `should equal` 2
                        //val response1 = list.find { it.id == employee1.id }!!
                        //response1 sameAs employee1
                        //val response2 = list.find { it.id == employee2.id }!!
                        //response2 sameAs employee2
                    }
                }
            }
        }
    }
})

fun newProduct(name: String) = Product(
    id = null,
    catId = 1,
    name = name,
    material = "material",
    color = "color",
    priceId = 1,
    notes = "notes",
    idMl = null,
    dateCreated = Date()
)