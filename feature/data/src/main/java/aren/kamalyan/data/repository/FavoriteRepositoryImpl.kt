package aren.kamalyan.data.repository

import aren.kamalyan.data.db.FavoriteDao
import aren.kamalyan.data.mapper.MapperFavoriteDbEntityToUiEntity
import aren.kamalyan.data.mapper.MapperRepoUIEntityToDbEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {
    override fun getAll(): Flow<List<RepoUiEntity>> =
        favoriteDao.findAllAsFlow().map {
            MapperFavoriteDbEntityToUiEntity().map(it)
        }

    override fun addToFavorite(repo: RepoUiEntity): Flow<Unit> = flow {
        val favoriteDbEntity = MapperRepoUIEntityToDbEntity().map(repo)
        favoriteDao.insert(favoriteDbEntity)
        emit(Unit)
    }

    override fun removeFromFavorite(repo: RepoUiEntity): Flow<Unit> = flow {
        favoriteDao.deleteById(repo.id)
        emit(Unit)
    }
}