package aren.kamalyan.domain.persistent

import android.content.SharedPreferences

/**
 * Holds only an instance of [SharedPreferences]
 * Do not put values that should be stored in shared preferences
 * Use [PrefManager] for that instead
 */
interface SharedPrefHolder {
    val preferences: SharedPreferences
}
