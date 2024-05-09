package aren.kamalyan.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RepoResultEntity(
    @SerialName("total_count") val total: Int = 0,
    val items: List<RepoEntity> = emptyList(),
)