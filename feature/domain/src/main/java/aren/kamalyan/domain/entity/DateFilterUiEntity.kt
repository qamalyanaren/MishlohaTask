package aren.kamalyan.domain.entity

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

sealed class DateFilterUiEntity {
    abstract fun toDateQuery(): String

    data object Yesterday : DateFilterUiEntity() {
        override fun toDateQuery(): String = formatDateQuery(1)
    }

    data object LastWeek : DateFilterUiEntity() {
        override fun toDateQuery(): String = formatDateQuery(7)
    }

    data object LastMonth : DateFilterUiEntity() {
        override fun toDateQuery(): String = formatDateQuery(30)
    }

    class CustomDays(private val daysAgo: Int) : DateFilterUiEntity() {
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