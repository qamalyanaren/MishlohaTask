package aren.kamalyan.mishlohatask.ui.home.filter


import androidx.lifecycle.viewModelScope
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.mishlohatask.common.base.BaseViewModel
import aren.kamalyan.mishlohatask.ui.home.delegate.SelectFilterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FilterItem(
    val dateFilter: DateFilterUiEntity,
    val isSelected: Boolean,
)

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val selectFilterDelegate: SelectFilterDelegate,
) : BaseViewModel() {

    private val _filterList = MutableStateFlow<List<FilterItem>>(emptyList())
    val filterList = _filterList.asStateFlow()

    private val _dismissSheet = Channel<Unit>(Channel.CONFLATED)
    val dismissSheet = _dismissSheet.receiveAsFlow()

    init {
        _filterList.value = DateFilterUiEntity.entries().map {
            FilterItem(it, it == selectFilterDelegate.selectedFilter.value)
        }
    }

    fun selectFilter(dateFilter: DateFilterUiEntity) {
        viewModelScope.launch {
            selectFilterDelegate.selectFilter(dateFilter)
            _dismissSheet.send(Unit)
        }
    }
}