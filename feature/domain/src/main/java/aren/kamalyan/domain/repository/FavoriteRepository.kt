package aren.kamalyan.domain.repository

import aren.kamalyan.domain.entity.RepoUiEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getAll(): Flow<List<RepoUiEntity>>

    fun addToFavorite(
        repo: RepoUiEntity
    ): Flow<Unit>

    fun removeFromFavorite(
        repo: RepoUiEntity
    ): Flow<Unit>
}