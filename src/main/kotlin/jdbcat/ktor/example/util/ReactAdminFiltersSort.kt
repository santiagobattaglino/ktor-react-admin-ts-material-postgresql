package jdbcat.ktor.example.util

import jdbcat.core.TemplatizeStatement

fun setRange(range: List<Int>, stmt: TemplatizeStatement, limitIndex: Int = 1, offsetIndex: Int = 2) {
    if (range[1] < 0) {
        stmt.setNull(limitIndex, java.sql.Types.NULL)
        stmt.setInt(offsetIndex, 0)
    } else {
        stmt.setInt(limitIndex, range[1] - range[0] + 1)
        if (range[0] == 0) {
            stmt.setInt(offsetIndex, range[0])
        } else {
            stmt.setInt(offsetIndex, range[0] + 1)
        }
    }
}
