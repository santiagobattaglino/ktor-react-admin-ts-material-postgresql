package jdbcat.ktor.example.route.v1.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jdbcat.ktor.example.db.model.Price
import java.util.Date

data class CreatePriceRequest(
    val cloth_1_name: String,
    val cloth_1_amount: String,
    val cloth_2_name: String?,
    val cloth_2_amount: String?,
    val elastEmb: String?,
    val elastCintura: String?,
    val elastBajoB: String?,
    val puntilla: String?,
    val bretel: String?,
    val argollas: String?,
    val ganchos: String?,
    val reguladores: String?,
    val manoDeObra: Int
) {
    // Here we set up the object to be saved
    fun toEntity() = Price(
        id = null,
        cloth_1_name = cloth_1_name,
        cloth_1_amount = cloth_1_amount,
        cloth_2_name = cloth_2_name,
        cloth_2_amount = cloth_2_amount,
        elastEmb = elastEmb,
        elastCintura = elastCintura,
        elastBajoB = elastBajoB,
        puntilla = puntilla,
        bretel = bretel,
        argollas = argollas,
        ganchos = ganchos,
        reguladores = reguladores,
        manoDeObra = manoDeObra,
        dateCreated = Date()
    )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class EditPriceRequest(
    val id: Int,
    val cloth_1_name: String,
    val cloth_1_amount: String,
    val cloth_2_name: String?,
    val cloth_2_amount: String?,
    val elastEmb: String?,
    val elastCintura: String?,
    val elastBajoB: String?,
    val puntilla: String?,
    val bretel: String?,
    val argollas: String?,
    val ganchos: String?,
    val reguladores: String?,
    val manoDeObra: Int,
    val dateCreated: Date?
) {
    fun toEntity(id: Int) = Price(
        id = id,
        cloth_1_name = cloth_1_name,
        cloth_1_amount = cloth_1_amount,
        cloth_2_name = cloth_2_name,
        cloth_2_amount = cloth_2_amount,
        elastEmb = elastEmb,
        elastCintura = elastCintura,
        elastBajoB = elastBajoB,
        puntilla = puntilla,
        bretel = bretel,
        argollas = argollas,
        ganchos = ganchos,
        reguladores = reguladores,
        manoDeObra = manoDeObra,
        dateCreated = Date()
    )
}

data class PriceResponse(
    val id: Int,
    val cloth_1_name: String,
    val cloth_1_amount: String,
    val cloth_2_name: String?,
    val cloth_2_amount: String?,
    val elastEmb: String?,
    val elastCintura: String?,
    val elastBajoB: String?,
    val puntilla: String?,
    val bretel: String?,
    val argollas: String?,
    val ganchos: String?,
    val reguladores: String?,
    val manoDeObra: Int,
    val manufacturingCost: Int,
    val dateCreated: Date
) {
    companion object {
        fun fromEntity(entity: Price, manufacturingCost: Int) =
            PriceResponse(
                id = entity.id!!,
                cloth_1_name = entity.cloth_1_name,
                cloth_1_amount = entity.cloth_1_amount,
                cloth_2_name = entity.cloth_2_name,
                cloth_2_amount = entity.cloth_2_amount,
                elastEmb = entity.elastEmb,
                elastCintura = entity.elastCintura,
                elastBajoB = entity.elastBajoB,
                puntilla = entity.puntilla,
                bretel = entity.bretel,
                argollas = entity.argollas,
                ganchos = entity.ganchos,
                reguladores = entity.reguladores,
                manoDeObra = entity.manoDeObra,
                manufacturingCost = manufacturingCost,
                dateCreated = entity.dateCreated
            )
    }
}
