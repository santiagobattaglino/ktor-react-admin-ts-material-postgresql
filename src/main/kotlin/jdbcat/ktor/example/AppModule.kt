package jdbcat.ktor.example

import com.zaxxer.hikari.HikariDataSource
import jdbcat.ktor.example.db.dao.*
import jdbcat.ktor.example.service.EmployeeReportService
import org.koin.dsl.module
import javax.sql.DataSource

/**
 * This is a heart of all dependency injection in this app.
 * We use Koin framework for dependency injections - https://insert-koin.io/
 */
val appModule = module(createdAtStart = true) {

    single {
        AppSettings(config = getProperty("mainConfig"))
    }

    // Data source. We use 1 data source per 1 database. One data source may supply multiple connections.
    single<DataSource> {
        HikariDataSource((get() as AppSettings).hikariMainDatabaseConfig)
    }

    // Inject DAO objects
    single { DepartmentDao(dataSource = get()) }
    single { EmployeeDao(dataSource = get()) }
    single { CategoryDao(dataSource = get()) }
    single { ProductDao(dataSource = get()) }
    single { UserDao(dataSource = get()) }
    single { ColorDao(dataSource = get()) }
    single { PriceDao(dataSource = get()) }
    single { StockDao(dataSource = get()) }
    single { SaleDao(dataSource = get()) }

    // Business logic objects
    single { EmployeeReportService(dataSource = get()) }
}
