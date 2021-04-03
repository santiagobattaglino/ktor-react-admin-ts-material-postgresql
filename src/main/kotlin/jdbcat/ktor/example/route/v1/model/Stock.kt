package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Stock
import jdbcat.ktor.example.db.model.StockByUser
import jdbcat.ktor.example.db.model.StockReport
import java.util.*

data class CreateStockRequest(
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
        val notes: String?
) {
    // Here we set up the object to be saved
    fun toEntity() = Stock(
            id = null,
            productId = productId,
            t1 = t1,
            t2 = t2,
            t3 = t3,
            t4 = t4,
            t5 = t5,
            t6 = t6,
            t7 = t7,
            t8 = t8,
            t9 = t9,
            t10 = t10,
            t11 = t11,
            total = total,
            userId = userId,
            notes = notes,
            dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditStockRequest(
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
        val notes: String?
) {
    fun toEntity(id: Int) = Stock(
            id = id,
            productId = productId,
            t1 = t1,
            t2 = t2,
            t3 = t3,
            t4 = t4,
            t5 = t5,
            t6 = t6,
            t7 = t7,
            t8 = t8,
            t9 = t9,
            t10 = t10,
            t11 = t11,
            total = total,
            userId = userId,
            notes = notes,
            dateCreated = Date()
    )
}

data class StockResponse(
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
        val t11: Int?,
        val total: Int?,
        val userId: Int,
        val notes: String?,
        val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Stock) =
                StockResponse(
                        id = entity.id!!,
                        productId = entity.productId,
                        t1 = entity.t1,
                        t2 = entity.t2,
                        t3 = entity.t3,
                        t4 = entity.t4,
                        t5 = entity.t5,
                        t6 = entity.t6,
                        t7 = entity.t7,
                        t8 = entity.t8,
                        t9 = entity.t9,
                        t10 = entity.t10,
                        t11 = entity.t11,
                        total = entity.total,
                        userId = entity.userId,
                        notes = entity.notes,
                        dateCreated = entity.dateCreated
                )
    }
}

data class StockUserResponse(
        val id: Int,
        val productId: Int,
        val total: Int?
) {
    companion object {
        fun fromEntity(entity: StockByUser) =
                StockUserResponse(
                        id = entity.id,
                        productId = entity.productId,
                        total = entity.total
                )
    }
}

data class StockDashboard(
        val data: List<StockUserResponse>,
        val total: Int
)

data class StockReportResponse(
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
    companion object {
        fun fromEntity(entity: StockReport) =
                StockReportResponse(
                        id = entity.id,
                        productId = entity.productId,
                        t1 = entity.t1,
                        t2 = entity.t2,
                        t3 = entity.t3,
                        t4 = entity.t4,
                        t5 = entity.t5,
                        t6 = entity.t6,
                        t7 = entity.t7,
                        t8 = entity.t8,
                        t9 = entity.t9,
                        t10 = entity.t10,
                        t11 = entity.t11
                )
    }
}
