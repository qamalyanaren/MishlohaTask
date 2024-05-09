package aren.kamalyan.data.extension

import java.time.Instant
import java.time.format.DateTimeParseException
import java.util.Date

fun String.toISODate(): Date? {
    return try {
        val instant = Instant.parse(this)
        Date.from(instant)
    } catch (e: DateTimeParseException) {
        null
    }
}