package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.integer
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.Date

object Products : Table(tableName = "products") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val catId = integer("cat_id").nonnull()
    val name = varchar("name", size = 100).nonnull()
    val material = varchar("material", size = 100).nonnull()
    val colorId = integer("color_id").nonnull()
    val idMl = integer("id_ml")
    val priceId = integer("price_id").nonnull()
    val manufacturingCost = integer("manufacturing_cost").nonnull()
    val notes = varchar("notes", size = 255)
    val dateCreated = javaDate("date_created").nonnull()
}

data class Product(
    val id: Int? = null,
    val catId: Int,
    val name: String,
    val material: String,
    val colorId: Int,
    val idMl: Int? = null,
    val priceId: Int,
    val manufacturingCost: Int,
    val notes: String? = null,
    val dateCreated: Date
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Products.id] = id
        }
        builder[Products.catId] = catId
        builder[Products.name] = name
        builder[Products.material] = material
        builder[Products.colorId] = colorId
        builder[Products.idMl] = idMl
        builder[Products.priceId] = priceId
        builder[Products.manufacturingCost] = manufacturingCost
        builder[Products.notes] = notes
        builder[Products.dateCreated] = dateCreated
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Product(
            id = extractor[Products.id],
            catId = extractor[Products.catId],
            name = extractor[Products.name],
            material = extractor[Products.material],
            colorId = extractor[Products.colorId],
            idMl = extractor[Products.idMl],
            priceId = extractor[Products.priceId],
            manufacturingCost = extractor[Products.manufacturingCost],
            notes = extractor[Products.notes],
            dateCreated = extractor[Products.dateCreated]
        )
    }
}
