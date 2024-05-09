package aren.kamalyan.domain.entity

import androidx.recyclerview.widget.DiffUtil
import java.util.Date


data class RepoUiEntity(
    val id: Long,
    val name: String,
    val description: String,
    val starsCount: Int,
    val ownerName: String?,
    val ownerAvatarUrl: String?,

    val language: String? = null,
    val forksCount: Int = 0,
    val createdAt: Date? = null,
    val htmlUrl: String? = null,

    var isFavorite: Boolean = false,
) {

    fun withFavoriteStatus(isFavorite: Boolean): RepoUiEntity {
        return this.copy(isFavorite = isFavorite)
    }

    companion object {
        val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RepoUiEntity>() {
            override fun areItemsTheSame(oldItem: RepoUiEntity, newItem: RepoUiEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RepoUiEntity, newItem: RepoUiEntity): Boolean =
                oldItem == newItem
        }
    }
}