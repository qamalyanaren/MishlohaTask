package aren.kamalyan.mishlohatask.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import aren.kamalyan.domain.delegate.ActionType
import aren.kamalyan.domain.delegate.FavoriteAction
import aren.kamalyan.domain.delegate.FavoriteActionDelegate
import aren.kamalyan.domain.delegate.PagingAction
import aren.kamalyan.domain.delegate.PagingActionDelegate
import aren.kamalyan.domain.delegate.withPagingActions
import aren.kamalyan.domain.entity.DateFilterUiEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.usecase.AddToFavoriteUseCase
import aren.kamalyan.domain.usecase.GithubRepoUseCase
import aren.kamalyan.domain.usecase.RemoveFromFavoriteUseCase
import aren.kamalyan.mishlohatask.common.base.BaseViewModel
import aren.kamalyan.mishlohatask.ui.home.delegate.SelectFilterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    filterDelegate: SelectFilterDelegate,
    private val favoriteActionDelegate: FavoriteActionDelegate,
    private val pagingActionDelegate: PagingActionDelegate,
    private val githubRepoUseCase: GithubRepoUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
) : BaseViewModel() {

    private val _selectedFilter by lazy { MutableStateFlow<DateFilterUiEntity>(DateFilterUiEntity.Yesterday) }
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _filterApplied = MutableStateFlow(false)
    val filterApplied = _filterApplied.asStateFlow()

    fun resetFilterAppliedFlag() {
        _filterApplied.value = false
    }

    init {
        filterDelegate.selectedFilter
            .onEach {
                _selectedFilter.value = it
                _filterApplied.value = true
            }
            .launchIn(viewModelScope)
        favoriteActionDelegate.favoriteAction
            .onEach { action ->
                val isFavorite = action.type == ActionType.ADD
                val pagingAction = PagingAction.UpdateFavorite(action.repoId, isFavorite)
                pagingActionDelegate.act(pagingAction)
            }
            .launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val repositories =
        selectedFilter.flatMapLatest {
            githubRepoUseCase(it)
        }
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .withPagingActions(pagingActionDelegate) { pagingData, action ->
                when (action) {
                    is PagingAction.UpdateFavorite -> {
                        pagingData.map {
                            if (action.id == it.id) it.copy(isFavorite = action.isFavorite) else it
                        }
                    }
                }
            }

    fun addRepoToFavorite(repo: RepoUiEntity) {
        addToFavoriteUseCase(repo)
            .flowOn(Dispatchers.IO)
            .onEach {
                favoriteActionDelegate.setFavoriteAction(
                    FavoriteAction(
                        repoId = repo.id,
                        type = ActionType.ADD
                    )
                )
            }
            .catch {
                //TODO: error hadling
                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    fun removeFromFavorite(repo: RepoUiEntity) {
        removeFromFavoriteUseCase(repo)
            .flowOn(Dispatchers.IO)
            .onEach {
                favoriteActionDelegate.setFavoriteAction(
                    FavoriteAction(
                        repoId = repo.id,
                        type = ActionType.REMOVE
                    )
                )
            }
            .catch {
                //TODO: error hadling
                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }
}