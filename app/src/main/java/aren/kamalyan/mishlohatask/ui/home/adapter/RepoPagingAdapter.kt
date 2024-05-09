package aren.kamalyan.mishlohatask.ui.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import aren.kamalyan.domain.entity.RepoUiEntity

class RepoPagingAdapter(
    private val onItemClicked: (repo: RepoUiEntity) -> Unit,
    private val onFavoriteItemClicked: (repo: RepoUiEntity) -> Unit,
) : PagingDataAdapter<RepoUiEntity, RepoViewHolder>(RepoUiEntity.REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder.create(parent, onItemClicked, onFavoriteItemClicked)

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val uiModel = getItem(position)
        holder.bind(uiModel)
    }
}