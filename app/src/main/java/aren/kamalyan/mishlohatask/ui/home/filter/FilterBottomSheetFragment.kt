package aren.kamalyan.mishlohatask.ui.home.filter

import android.content.res.Resources
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import aren.kamalyan.coreui.delegate.viewBinding
import aren.kamalyan.coreui.extension.collectWhenStarted
import aren.kamalyan.coreui.extension.dp
import aren.kamalyan.coreui.utils.AdaptiveSpacingItemDecoration
import aren.kamalyan.mishlohatask.R
import aren.kamalyan.mishlohatask.common.base.BaseBottomSheetDialogFragment
import aren.kamalyan.mishlohatask.databinding.BottomSheetFilterBinding
import aren.kamalyan.mishlohatask.ui.home.filter.adapter.FilterAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheetFragment :
    BaseBottomSheetDialogFragment<BottomSheetFilterBinding, FilterViewModel>() {
    override val viewModel: FilterViewModel by viewModels()

    override val layoutId: Int = R.layout.bottom_sheet_filter

    private val binding by viewBinding(BottomSheetFilterBinding::bind)

    private val cryptoAdapter by lazy {
        FilterAdapter(
            onItemClicked = {
                viewModel.selectFilter(it.dateFilter)
            }
        )
    }

//    override fun onStart() {
//        super.onStart()
//        val bottomSheetInternal =
//            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        bottomSheetInternal?.let {
//            val computedHeight = (Resources.getSystem().displayMetrics.heightPixels)
//            it.updateLayoutParams { this.height = computedHeight }
//            BottomSheetBehavior.from(it).peekHeight = computedHeight
//        }
//    }


    override fun initView() = with(binding) {
        rvFilter.adapter = cryptoAdapter
        rvFilter.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvFilter.addItemDecoration(AdaptiveSpacingItemDecoration(16.dp.toInt()))
    }

    override fun initObservers() {
        collectWhenStarted(viewModel.filterList, cryptoAdapter::submitList)
        collectWhenStarted(viewModel.dismissSheet) { dismiss() }
    }

    companion object {
        const val TAG = "FilterBottomSheetFragment"

        fun newInstance() = FilterBottomSheetFragment()
    }
}