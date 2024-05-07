package aren.kamalyan.mishlohatask.ui.home.delegate

import aren.kamalyan.domain.entity.DateFilterUiEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectFilterDelegate @Inject constructor() {

    private val _selectedFilter = MutableStateFlow<DateFilterUiEntity>(DateFilterUiEntity.Yesterday)
    val selectedFilter = _selectedFilter.asStateFlow()

    suspend fun selectFilter(filter: DateFilterUiEntity) {
        _selectedFilter.emit(filter)
    }

}