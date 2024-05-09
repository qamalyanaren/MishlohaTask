package aren.kamalyan.domain.usecase

import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RemoveFromFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(repo: RepoUiEntity): Flow<Unit> =
        repository.removeFromFavorite(repo)
}