package aren.kamalyan.mishlohatask.ui.home.filter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import aren.kamalyan.mishlohatask.ui.home.filter.FilterItem

class FilterAdapter(
    private val onItemClicked: (repo: FilterItem) -> Unit
) : ListAdapter<FilterItem, FilterViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder =
        FilterViewHolder.create(parent, onItemClicked)

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val uiModel = getItem(position)
        holder.bind(uiModel)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<FilterItem>() {
            override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
                return oldItem.dateFilter == newItem.dateFilter
            }

            override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean =
                oldItem == newItem
        }
    }
}