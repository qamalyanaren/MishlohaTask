package aren.kamalyan.domain.delegate

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

class FavoriteAction(
    val repoId: Long,
    val type: ActionType
)

enum class ActionType {
    ADD,
    REMOVE;
}

@Singleton
class FavoriteActionDelegate @Inject constructor() {

    private val _favoriteAction = MutableSharedFlow<FavoriteAction>()
    val favoriteAction = _favoriteAction.asSharedFlow()

    suspend fun setFavoriteAction(action: FavoriteAction) {
        _favoriteAction.emit(action)
    }

}