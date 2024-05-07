package aren.kamalyan.data.mapper

import aren.kamalyan.data.entity.RepoEntity
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.domain.utils.Mapper


class MapperRepoEntityToUiEntity :
    Mapper<RepoEntity, RepoUiEntity> {
    override fun map(from: RepoEntity): RepoUiEntity {
        return RepoUiEntity(
            id = from.id,
            name = from.name,
            description = from.description.orEmpty(),
            starsCount = from.starsCount,
            ownerAvatarUrl = from.owner?.avatarUrl,
            ownerName = from.owner?.login
        )
    }
}