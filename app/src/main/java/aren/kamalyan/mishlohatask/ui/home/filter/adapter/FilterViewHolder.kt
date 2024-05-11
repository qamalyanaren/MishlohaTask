package aren.kamalyan.mishlohatask.ui.home.filter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aren.kamalyan.mishlohatask.databinding.ItemFilterBinding
import aren.kamalyan.mishlohatask.ui.home.filter.FilterItem

class FilterViewHolder(
    private val binding: ItemFilterBinding,
    onItemClicked: (filter: FilterItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var filterItem: FilterItem? = null

    init {
        binding.root.setOnClickListener {
            filterItem?.let(onItemClicked)
        }
    }

    fun bind(filter: FilterItem) = with(binding) {
        filterItem = filter
        tvFilter.setText(filter.dateFilter.titleRes)
        ivFilter.isSelected = filter.isSelected
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: (repo: FilterItem) -> Unit): FilterViewHolder {
            val binding = ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FilterViewHolder(binding, onItemClicked)
        }
    }
}
