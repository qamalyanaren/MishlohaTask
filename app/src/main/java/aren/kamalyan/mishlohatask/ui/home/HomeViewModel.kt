package aren.kamalyan.mishlohatask.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.domain.usecase.GithubRepoSearchUseCase
import aren.kamalyan.mishlohatask.common.base.BaseViewModel
import aren.kamalyan.mishlohatask.ui.home.delegate.SelectFilterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    filterDelegate: SelectFilterDelegate,
    private val githubRepoSearchUseCase: GithubRepoSearchUseCase,
) : BaseViewModel() {

    private val _selectedFilter by lazy { MutableStateFlow<DateFilterUiEntity>(DateFilterUiEntity.Yesterday) }
    val selectedFilter = _selectedFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val repositories =
        selectedFilter.flatMapLatest {
            githubRepoSearchUseCase(it)
        }
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)


    init {
        filterDelegate.selectedFilter
            .onEach { _selectedFilter.value = it }
            .launchIn(viewModelScope)
    }
}