package jdbcat.ktor.example.db.model

data class Filter(
        val id: List<Int>?,
        val name: String?,
        val saleId: Int?,
        val productId: Int?,
        val userId: Int?,
        val catId: Int?,
        val q: String?
)
