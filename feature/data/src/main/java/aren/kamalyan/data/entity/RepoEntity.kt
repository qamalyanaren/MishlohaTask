package aren.kamalyan.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RepoEntity(
    val id: Long = -1L,
    val name: String,
    val description: String?,
    @SerialName("stargazers_count")
    val starsCount: Int = 0,
    val owner: OwnerEntity?
) {
    @Serializable
    class OwnerEntity(
        val login: String,
        @SerialName("avatar_url")
        val avatarUrl: String?
    )
}