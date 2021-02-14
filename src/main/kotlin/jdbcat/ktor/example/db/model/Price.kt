package jdbcat.ktor.example.db.model

import jdbcat.core.ColumnValueBuilder
import jdbcat.core.ColumnValueExtractor
import jdbcat.core.Table
import jdbcat.core.integer
import jdbcat.core.varchar
import jdbcat.dialects.pg.pgSerial
import jdbcat.ext.javaDate
import java.util.Date

object Prices : Table(tableName = "prices") {
    val id = pgSerial("id", specifier = "PRIMARY KEY")
    val cloth_1_name = varchar("cloth_1_name", 100).nonnull()
    val cloth_1_amount = varchar("cloth_1_amount", 100).nonnull() // m2
    val cloth_2_name = varchar("cloth_2_name", 5)
    val cloth_2_amount = varchar("cloth_2_amount", 5) // m2
    val elastEmb = varchar("elast_emb", 5) // m
    val elastCintura = varchar("elast_cintura", 5) // m
    val elastBajoB = varchar("elast_bajo_b", 5) // m
    val puntilla = varchar("puntilla", 5) // m
    val bretel = varchar("bretel", 5) // m
    val argollas = varchar("argollas", 2) // unidades
    val ganchos = varchar("ganchos", 2) // unidades
    val reguladores = varchar("reguladores", 2) // unidades
    val manoDeObra = integer("mano_de_obra").nonnull() // $
    val dateCreated = javaDate("date_created").nonnull()
}

data class Price(
    val id: Int? = null,
    val cloth_1_name: String,
    val cloth_1_amount: String,
    val cloth_2_name: String? = null,
    val cloth_2_amount: String? = null,
    val elastEmb: String? = null,
    val elastCintura: String? = null,
    val elastBajoB: String? = null,
    val puntilla: String? = null,
    val bretel: String? = null,
    val argollas: String? = null,
    val ganchos: String? = null,
    val reguladores: String? = null,
    val manoDeObra: Int,
    val dateCreated: Date
) {
    fun copyValuesTo(builder: ColumnValueBuilder) {
        if (id != null) {
            builder[Prices.id] = id
        }
        builder[Prices.cloth_1_name] = cloth_1_name
        builder[Prices.cloth_1_amount] = cloth_1_amount
        builder[Prices.cloth_2_name] = cloth_2_name
        builder[Prices.cloth_2_amount] = cloth_2_amount
        builder[Prices.elastEmb] = elastEmb
        builder[Prices.elastCintura] = elastCintura
        builder[Prices.elastBajoB] = elastBajoB
        builder[Prices.puntilla] = puntilla
        builder[Prices.bretel] = bretel
        builder[Prices.argollas] = argollas
        builder[Prices.ganchos] = ganchos
        builder[Prices.reguladores] = reguladores
        builder[Prices.manoDeObra] = manoDeObra
        builder[Prices.dateCreated] = dateCreated
    }

    companion object {
        fun extractFrom(extractor: ColumnValueExtractor) = Price(
            id = extractor[Prices.id],
            cloth_1_name = extractor[Prices.cloth_1_name],
            cloth_1_amount = extractor[Prices.cloth_1_amount],
            cloth_2_name = extractor[Prices.cloth_2_name],
            cloth_2_amount = extractor[Prices.cloth_2_amount],
            elastEmb = extractor[Prices.elastEmb],
            elastCintura = extractor[Prices.elastCintura],
            elastBajoB = extractor[Prices.elastBajoB],
            puntilla = extractor[Prices.puntilla],
            bretel = extractor[Prices.bretel],
            argollas = extractor[Prices.argollas],
            ganchos = extractor[Prices.ganchos],
            reguladores = extractor[Prices.reguladores],
            manoDeObra = extractor[Prices.manoDeObra],
            dateCreated = extractor[Prices.dateCreated]
        )
    }
}
