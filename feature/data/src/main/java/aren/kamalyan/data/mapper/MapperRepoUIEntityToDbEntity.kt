package aren.kamalyan.data.mapper

import aren.kamalyan.data.db.FavoriteDBEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.utils.Mapper
import java.util.Date


class MapperRepoUIEntityToDbEntity :
    Mapper<RepoUiEntity, FavoriteDBEntity> {
    override fun map(from: RepoUiEntity): FavoriteDBEntity {
        return FavoriteDBEntity(
            id = from.id,
            name = from.name,
            description = from.description,
            starsCount = from.starsCount,
            ownerAvatarUrl = from.ownerAvatarUrl,
            ownerName = from.ownerName,

            language = from.language,
            forksCount = from.forksCount,
            createdAt = from.createdAt ?: Date(),
            htmlUrl = from.htmlUrl,
        )
    }
}