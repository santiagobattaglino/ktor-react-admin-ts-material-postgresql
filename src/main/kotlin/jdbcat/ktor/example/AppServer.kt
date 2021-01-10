package jdbcat.ktor.example

import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.ApplicationStarted
import io.ktor.application.ApplicationStopped
import io.ktor.application.install
import mu.KotlinLogging
import org.koin.Logger.SLF4JLogger
import org.koin.ktor.ext.Koin

// All REST calls must specify version, e.g. http://localhost/api/v1/healthcheck
const val serviceApiVersionV1 = "api/v1"

private val logger = KotlinLogging.logger { }
// TODO implement https://dev.to/viniciusccarvalho/graceful-shutdown-of-ktor-applications-1h53 for stop ktor
/**
 * Application's ENTRY POINT.
 * This method is called by Ktor and this entry point is configured in /resources/application.conf
 */
@Suppress("unused")
fun Application.main() {

    environment.monitor.subscribe(ApplicationStarted){
        println("ApplicationStarted")
    }
    environment.monitor.subscribe(ApplicationStopped){
        println("ApplicationStopped")
    }

    val mainConfig = ConfigFactory.load("main.conf")
    // Add Koin DI support to Ktor
    install(Koin) {
        SLF4JLogger()
        modules(appModule)
        properties(mapOf("mainConfig" to mainConfig))
    }

    // Initial bootstrap
    bootstrap()
}
