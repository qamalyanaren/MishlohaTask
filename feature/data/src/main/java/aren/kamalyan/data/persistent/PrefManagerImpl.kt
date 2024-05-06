package aren.kamalyan.data.persistent

import android.content.SharedPreferences
import aren.kamalyan.data.persistent.PrefDelegate
import aren.kamalyan.domain.persistent.PrefManager
import aren.kamalyan.domain.persistent.SharedPrefHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefManagerImpl @Inject constructor(
    preferences: SharedPreferences
) : PrefManager, SharedPrefHolder {

    override val preferences: SharedPreferences by lazy { preferences }

    override var testData: String by PrefDelegate("")

    override fun clear() {
        testData = ""
    }
}