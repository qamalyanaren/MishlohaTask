package aren.kamalyan.mishlohatask.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import aren.kamalyan.domain.entity.RepoUiEntity
import aren.kamalyan.mishlohatask.databinding.ItemRepoBinding
import coil.load

class RepoViewHolder(
    private val binding: ItemRepoBinding,
    onItemClicked: (repo: RepoUiEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var repoEntity: RepoUiEntity? = null

    init {
        binding.root.setOnClickListener {
            repoEntity?.let {
                onItemClicked.invoke(it)
            }
        }
    }

    fun bind(repo: RepoUiEntity?) = with(binding) {
        if (repo == null) {
//            tvName.text = getString(RString.repo_item_loading)
            tvDescription.isVisible = false
//            tvStars.text = getString(RString.repo_item_unknown)
        } else {
            repoEntity = repo
            tvName.text = repo.name
            tvDescription.isVisible = repo.description.isNotBlank()
            tvDescription.text = repo.description
            tvStars.text = "⭐ ${repo.starsCount}"
            ivAvatar.load(repo.ownerAvatarUrl)
        }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: (repo: RepoUiEntity) -> Unit): RepoViewHolder {
            val binding = ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RepoViewHolder(binding, onItemClicked)
        }
    }
}
