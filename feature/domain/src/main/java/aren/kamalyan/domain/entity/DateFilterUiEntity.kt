package aren.kamalyan.domain.entity

import androidx.annotation.StringRes
import aren.kamalyan.domain.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

sealed class DateFilterUiEntity(@StringRes val titleRes: Int) {
    abstract fun toDateQuery(): String

    data object Yesterday : DateFilterUiEntity(R.string.filter_yesterday) {
        override fun toDateQuery(): String = formatDateQuery(1)
    }

    data object LastWeek : DateFilterUiEntity(R.string.filter_weak_ago) {
        override fun toDateQuery(): String = formatDateQuery(7)
    }

    data object LastMonth : DateFilterUiEntity(R.string.filter_month_ago) {
        override fun toDateQuery(): String = formatDateQuery(30)
    }

    class CustomDays(private val daysAgo: Int) : DateFilterUiEntity(R.string.filter_custom) {
        override fun toDateQuery(): String = formatDateQuery(daysAgo)
    }

    companion object {
        private fun formatDateQuery(daysAgo: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return "created:>${dateFormat.format(calendar.time)}"
        }

        fun entries(): List<DateFilterUiEntity> = listOf(Yesterday, LastWeek, LastMonth)
    }
}