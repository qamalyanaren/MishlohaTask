package aren.kamalyan.domain.payload

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

sealed class DateFilterPayload {
    abstract fun toDateQuery(): String

    data object Yesterday : DateFilterPayload() {
        override fun toDateQuery(): String = formatDateQuery(1)
    }

    data object LastWeek : DateFilterPayload() {
        override fun toDateQuery(): String = formatDateQuery(7)
    }

    data object LastMonth : DateFilterPayload() {
        override fun toDateQuery(): String = formatDateQuery(30)
    }

    class CustomDays(private val daysAgo: Int) : DateFilterPayload() {
        override fun toDateQuery(): String = formatDateQuery(daysAgo)
    }

    companion object {
        private fun formatDateQuery(daysAgo: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return "created:>${dateFormat.format(calendar.time)}"
        }
    }
}