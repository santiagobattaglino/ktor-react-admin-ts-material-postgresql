package jdbcat.ktor.example.client

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.html.*
import jdbcat.ktor.example.db.model.Product
import kotlinx.html.*

suspend fun printProducts(client: HttpClient, call: ApplicationCall) {
    val model = client.get<List<Product>>(port = 8080, path = "/api/v1/products?range=[0,-1]")

    call.respondHtml {
        head {
            title { +"Hello, ${call.principal<UserIdPrincipal>()?.name}!" }
        }
        body {
            model.forEach { product ->
                //val item = client.get<Product>(port = 8080, path = "/v1/item/${product.id}")
                p {
                    +"!! ${product.name} !!"
                }
            }
            widget {
                +"Un Widget"
            }
        }
    }
}

@HtmlTagMarker
fun FlowContent.widget(body: FlowContent.() -> Unit) {
    div { body() }
}