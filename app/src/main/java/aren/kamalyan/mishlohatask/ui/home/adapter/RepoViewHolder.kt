package aren.kamalyan.mishlohatask.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aren.kamalyan.coreui.extension.getString
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.mishlohatask.databinding.ItemRepoBinding
import aren.kamalyan.mishlohatask.utils.RDrawable
import aren.kamalyan.mishlohatask.utils.RString
import coil.load

class RepoViewHolder(
    private val binding: ItemRepoBinding,
    private val onItemClicked: (repo: RepoUiEntity) -> Unit,
    private val onFavoriteClicked: (repo: RepoUiEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var repoEntity: RepoUiEntity? = null

    init {
        binding.root.setOnClickListener {
            repoEntity?.let(onItemClicked)
        }
        binding.ivFavorite.setOnClickListener {
            repoEntity?.let(onFavoriteClicked)
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(repo: RepoUiEntity?) = with(binding) {
        repoEntity = repo
        if (repo == null) {
            tvName.text = "..."
        } else {
            tvName.text = repo.ownerName?.let { it + "/" + repo.name } ?: repo.name
            tvDescription.text = repo.description ?: getString(RString.repo_no_description)
            tvStars.text = "⭐ ${repo.starsCount}"
            ivFavorite.isSelected = repo.isFavorite
            ivAvatar.load(repo.ownerAvatarUrl) {
                crossfade(true)
                error(RDrawable.avatar)
            }

        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClicked: (repo: RepoUiEntity) -> Unit,
            onFavoriteClicked: (repo: RepoUiEntity) -> Unit
        ): RepoViewHolder {
            val binding = ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RepoViewHolder(binding, onItemClicked, onFavoriteClicked)
        }
    }
}
