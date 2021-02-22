package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.integer
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.Date

object StockMovements : Table(tableName = "stock_movements") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val productId = integer("product_id").nonnull()
    val size = integer("size").nonnull()
    val quantity = integer("quantity").nonnull()
    val userId = integer("user_id").nonnull()
    val notes = varchar("notes", size = 255)
    val dateCreated = javaDate("date_created").nonnull()
}

data class Stock(
    val id: Int? = null,
    val productId: Int,
    val size: Int,
    val quantity: Int,
    val userId: Int,
    val notes: String?,
    val dateCreated: Date
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[StockMovements.id] = id
        }
        builder[StockMovements.productId] = productId
        builder[StockMovements.size] = size
        builder[StockMovements.quantity] = quantity
        builder[StockMovements.userId] = userId
        builder[StockMovements.notes] = notes
        builder[StockMovements.dateCreated] = dateCreated
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Stock(
            id = extractor[StockMovements.id],
            productId = extractor[StockMovements.productId],
            size = extractor[StockMovements.size],
            quantity = extractor[StockMovements.quantity],
            userId = extractor[StockMovements.userId],
            notes = extractor[StockMovements.notes],
            dateCreated = extractor[StockMovements.dateCreated]
        )
    }
}
