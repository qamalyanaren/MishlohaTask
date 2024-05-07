package aren.kamalyan.mishlohatask.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.domain.usecase.GithubRepoSearchUseCase
import aren.kamalyan.mishlohatask.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepoSearchUseCase: GithubRepoSearchUseCase,
) : BaseViewModel() {

    val repositories =
        githubRepoSearchUseCase(DateFilterUiEntity.Yesterday)
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
}