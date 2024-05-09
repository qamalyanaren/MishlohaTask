package aren.kamalyan.mishlohatask.ui.favorite

import androidx.lifecycle.viewModelScope
import aren.kamalyan.domain.delegate.ActionType
import aren.kamalyan.domain.delegate.FavoriteAction
import aren.kamalyan.domain.delegate.FavoriteActionDelegate
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.usecase.FavoriteRepoUseCase
import aren.kamalyan.domain.usecase.RemoveFromFavoriteUseCase
import aren.kamalyan.mishlohatask.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepoUseCase: FavoriteRepoUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val favoriteActionDelegate: FavoriteActionDelegate,
) : BaseViewModel() {

    private val _repos = MutableStateFlow<List<RepoUiEntity>>(listOf())
    val repos = _repos.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        favoriteRepoUseCase()
            .flowOn(Dispatchers.IO)
            .onEach {
                _repos.value = it
            }
            .catch {

            }.launchIn(viewModelScope)

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