package aren.kamalyan.domain.usecase

import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavoriteRepoUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<RepoUiEntity>> =
        repository.getAll()
}