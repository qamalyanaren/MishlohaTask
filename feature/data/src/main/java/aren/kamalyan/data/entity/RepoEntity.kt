package aren.kamalyan.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RepoEntity(
    val id: Long = -1L,
    val name: String,
    val description: String?, //TODO: default no description
    @SerialName("stargazers_count")
    val starsCount: Int = 0,
    val owner: OwnerEntity?,

    val language: String?,
    val forks: Int = 0,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("html_url")
    val htmlUrl: String?,
) {
    @Serializable
    class OwnerEntity(
        val login: String,
        @SerialName("avatar_url")
        val avatarUrl: String? //TODO: default no avatar
    )
}