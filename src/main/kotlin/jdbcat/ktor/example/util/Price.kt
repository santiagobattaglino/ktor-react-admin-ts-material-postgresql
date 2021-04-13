package jdbcat.ktor.example.util

import jdbcat.ktor.example.Config.PRICE_MULT_CAPILLA
import jdbcat.ktor.example.Config.PRICE_MULT_ML
import jdbcat.ktor.example.Config.PRICE_MULT_POR_MAYOR
import jdbcat.ktor.example.Config.PRICE_MULT_POR_MENOR
import kotlin.math.round

enum class Price(val id: Int) {
    MAYOR(9),
    CAPILLA(10),
    MENOR(11),
    ML(12),
    OTRO(13)
}

fun precioMayor(manufacturingCost: Int): Int {
    return round(manufacturingCost * PRICE_MULT_POR_MAYOR).toInt()
}

fun precioCapilla(manufacturingCost: Int): Int {
    return round(precioMayor(manufacturingCost) * PRICE_MULT_CAPILLA).toInt()
}

fun precioMenor(manufacturingCost: Int): Int {
    return precioMayor(manufacturingCost) * PRICE_MULT_POR_MENOR
}

fun precioMl(manufacturingCost: Int): Int {
    return round(precioMenor(manufacturingCost) + precioMenor(manufacturingCost) * PRICE_MULT_ML).toInt()
}

fun getPriceById(total: Int, priceId: Int): Int {
    return when (priceId) {
        Price.MAYOR.id -> precioMayor(total)
        Price.CAPILLA.id -> precioCapilla(total)
        Price.MENOR.id -> precioMenor(total)
        Price.ML.id -> precioMl(total)
        else -> total
    }
}
