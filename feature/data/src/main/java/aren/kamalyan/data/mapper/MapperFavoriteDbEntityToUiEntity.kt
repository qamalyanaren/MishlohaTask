package aren.kamalyan.data.mapper

import aren.kamalyan.data.db.FavoriteDBEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.utils.Mapper


class MapperFavoriteDbEntityToUiEntity :
    Mapper<FavoriteDBEntity, RepoUiEntity> {
    override fun map(from: FavoriteDBEntity): RepoUiEntity {
        return RepoUiEntity(
            id = from.id,
            name = from.name,
            description = from.description,
            starsCount = from.starsCount,
            ownerAvatarUrl = from.ownerAvatarUrl,
            ownerName = from.ownerName,

            language = from.language,
            forksCount = from.forksCount,
            createdAt = from.createdAt,
            htmlUrl = from.htmlUrl,
            isFavorite = true,
        )
    }
}