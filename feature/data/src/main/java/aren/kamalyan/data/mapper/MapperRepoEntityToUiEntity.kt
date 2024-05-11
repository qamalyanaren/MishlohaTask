package aren.kamalyan.data.mapper

import aren.kamalyan.data.db.FavoriteDBEntity
import aren.kamalyan.data.entity.RepoEntity
import aren.kamalyan.data.extension.toISODate
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.utils.Mapper


class MapperRepoEntityToUiEntity(private val favorites: List<FavoriteDBEntity>) :
    Mapper<RepoEntity, RepoUiEntity> {
    override fun map(from: RepoEntity): RepoUiEntity {
        return RepoUiEntity(
            id = from.id,
            name = from.name,
            description = from.description,
            starsCount = from.starsCount,
            ownerAvatarUrl = from.owner?.avatarUrl,
            ownerName = from.owner?.login,

            language = from.language,
            forksCount = from.forks,
            createdAt = from.createdAt.toISODate(),
            htmlUrl = from.htmlUrl,

            isFavorite = favorites.any { it.id == from.id }
        )
    }
}