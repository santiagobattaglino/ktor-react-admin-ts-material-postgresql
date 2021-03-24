package jdbcat.ktor.example.route.v1

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import jdbcat.core.tx
import jdbcat.ktor.example.db.dao.StockDao
import jdbcat.ktor.example.db.model.Filter
import jdbcat.ktor.example.route.v1.model.CreateStockRequest
import jdbcat.ktor.example.route.v1.model.EditStockRequest
import jdbcat.ktor.example.route.v1.model.StockResponse
import jdbcat.ktor.example.route.v1.model.StockUserResponse
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

fun Route.stockRoute() {

    val dataSource by inject<DataSource>()
    val stockDao by inject<StockDao>()

    route("/stock") {

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
                val stockMovementsResponse = stockDao
                    .selectAll(range = range, sort = sort)
                    .map { StockResponse.fromEntity(it) }
                    .toList()
                call.response.header("X-Total-Count", stockDao.countAll())
                call.respond(stockMovementsResponse)
            }
        }

        // get by id
        get("/{id}") { _ ->
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                val stockResponse = stockDao
                    .select(id = id)
                    .let { StockResponse.fromEntity(it) }
                call.respond(stockResponse)
            }
        }

        // get by userId
        get("/user/{userId}") { _ ->
            val userId = call.parameters["userId"]!!.toInt()
            dataSource.tx {
                val stockList = stockDao
                        .selectByUserId(userId = userId)
                        .map { StockUserResponse.fromEntity(it) }
                        .toList()
                call.response.header("X-Total-Count", stockList.size)

                /*val total = stockList.map {
                    it.quantity
                }.sum()
                val response = StockDashboard(stockList, total)*/

                call.respond(stockList)
            }
        }

        // post add
        post("/") {
            val addStockRequest = call.receive<CreateStockRequest>()
            val stockToAdd = addStockRequest.toEntity()
            dataSource.tx {
                val stockResponse = stockDao
                    .insert(item = stockToAdd)
                    .let { StockResponse.fromEntity(it) }
                call.respond(stockResponse)
            }
        }

        // put update
        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val editStockRequest = call.receive<EditStockRequest>()
            val stockToUpdate = editStockRequest.toEntity(id = id)
            dataSource.tx {
                val stockResponse = stockDao
                    .update(item = stockToUpdate)
                    .let { StockResponse.fromEntity(it) }
                call.respond(stockResponse)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            dataSource.tx {
                stockDao.delete(id = id)
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
