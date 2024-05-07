package aren.kamalyan.domain.entity


data class RepoUiEntity(
    val id: Long,
    val name: String,
    val description: String,
    val starsCount: Int,

    val ownerName: String?,
    val ownerAvatarUrl: String?,
)