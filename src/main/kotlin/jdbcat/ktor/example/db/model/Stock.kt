package jdbcat.ktor.example.db.model

import jdbcat.core.*
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.*

object StockMovements : Table(tableName = "stock_movements") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val productId = integer("product_id").nonnull()
    val t1 = integer("t1")
    val t2 = integer("t2")
    val t3 = integer("t3")
    val t4 = integer("t4")
    val t5 = integer("t5")
    val t6 = integer("t6")
    val t7 = integer("t7")
    val t8 = integer("t8")
    val t9 = integer("t9")
    val t10 = integer("t10")
    val t11 = integer("t11")
    val total = integer("total")
    val userId = integer("user_id").nonnull()
    val notes = varchar("notes", size = 255)
    val dateCreated = javaDate("date_created").nonnull()
}

data class Stock(
        val id: Int? = null,
        val productId: Int,
        val t1: Int?,
        val t2: Int?,
        val t3: Int?,
        val t4: Int?,
        val t5: Int?,
        val t6: Int?,
        val t7: Int?,
        val t8: Int?,
        val t9: Int?,
        val t10: Int?,
        val t11: Int?,
        val total: Int?,
        val userId: Int,
        val notes: String?,
        val dateCreated: Date
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[StockMovements.id] = id
        }
        builder[StockMovements.productId] = productId
        builder[StockMovements.t1] = t1
        builder[StockMovements.t2] = t2
        builder[StockMovements.t3] = t3
        builder[StockMovements.t4] = t4
        builder[StockMovements.t5] = t5
        builder[StockMovements.t6] = t6
        builder[StockMovements.t7] = t7
        builder[StockMovements.t8] = t8
        builder[StockMovements.t9] = t9
        builder[StockMovements.t10] = t10
        builder[StockMovements.t11] = t11
        builder[StockMovements.total] = total
        builder[StockMovements.userId] = userId
        builder[StockMovements.notes] = notes
        builder[StockMovements.dateCreated] = dateCreated
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Stock(
                id = extractor[StockMovements.id],
                productId = extractor[StockMovements.productId],
                t1 = extractor[StockMovements.t1],
                t2 = extractor[StockMovements.t2],
                t3 = extractor[StockMovements.t3],
                t4 = extractor[StockMovements.t4],
                t5 = extractor[StockMovements.t5],
                t6 = extractor[StockMovements.t6],
                t7 = extractor[StockMovements.t7],
                t8 = extractor[StockMovements.t8],
                t9 = extractor[StockMovements.t9],
                t10 = extractor[StockMovements.t10],
                t11 = extractor[StockMovements.t11],
                total = extractor[StockMovements.total],
                userId = extractor[StockMovements.userId],
                notes = extractor[StockMovements.notes],
                dateCreated = extractor[StockMovements.dateCreated]
        )
    }
}

data class StockByUser(
        val id: Int,
        val productId: Int,
        val total: Int?
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        builder[StockMovements.id] = id
        builder[StockMovements.productId] = productId
        builder[StockMovements.total] = total
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = StockByUser(
                id = extractor[StockMovements.id],
                productId = extractor[StockMovements.productId],
                total = extractor[StockMovements.total]
        )
    }
}

data class StockReport(
        val id: Int,
        val productId: Int,
        val t1: Int?,
        val t2: Int?,
        val t3: Int?,
        val t4: Int?,
        val t5: Int?,
        val t6: Int?,
        val t7: Int?,
        val t8: Int?,
        val t9: Int?,
        val t10: Int?,
        val t11: Int?
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        builder[StockMovements.id] = id
        builder[StockMovements.productId] = productId
        builder[StockMovements.t1] = t1
        builder[StockMovements.t2] = t2
        builder[StockMovements.t3] = t3
        builder[StockMovements.t4] = t4
        builder[StockMovements.t5] = t5
        builder[StockMovements.t6] = t6
        builder[StockMovements.t7] = t7
        builder[StockMovements.t8] = t8
        builder[StockMovements.t9] = t9
        builder[StockMovements.t10] = t10
        builder[StockMovements.t11] = t11
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = StockReport(
                id = extractor[StockMovements.id],
                productId = extractor[StockMovements.productId],
                t1 = extractor[StockMovements.t1],
                t2 = extractor[StockMovements.t2],
                t3 = extractor[StockMovements.t3],
                t4 = extractor[StockMovements.t4],
                t5 = extractor[StockMovements.t5],
                t6 = extractor[StockMovements.t6],
                t7 = extractor[StockMovements.t7],
                t8 = extractor[StockMovements.t8],
                t9 = extractor[StockMovements.t9],
                t10 = extractor[StockMovements.t10],
                t11 = extractor[StockMovements.t11]
        )
    }
}
