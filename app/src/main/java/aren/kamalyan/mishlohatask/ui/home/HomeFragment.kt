package aren.kamalyan.mishlohatask.ui.home


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import aren.kamalyan.coreui.delegate.viewBinding
import aren.kamalyan.coreui.extension.collectLatestWhenStarted
import aren.kamalyan.coreui.extension.collectWhenStarted
import aren.kamalyan.coreui.extension.dp
import aren.kamalyan.coreui.utils.AdaptiveSpacingItemDecoration
import aren.kamalyan.mishlohatask.R
import aren.kamalyan.mishlohatask.common.base.BaseFragment
import aren.kamalyan.mishlohatask.databinding.FragmentHomeBinding
import aren.kamalyan.mishlohatask.ui.details.RepoDetailDialog
import aren.kamalyan.mishlohatask.ui.home.adapter.RepoPagingAdapter
import aren.kamalyan.mishlohatask.ui.home.filter.FilterBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var repoDialog: RepoDetailDialog

    override val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy {
        RepoPagingAdapter(
            onItemClicked = { repo ->
                repoDialog =
                    RepoDetailDialog(
                        this,
                        repo
                    );
                repoDialog.show()
            },
            onFavoriteClicked = viewModel::onFavoriteItemClicked
        ).also { adapter ->
            adapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.NotLoading && viewModel.filterApplied.value) {
                    binding.rvRepositories.scrollToPosition(0)
                    viewModel.resetFilterAppliedFlag()
                }
                binding.loadingView.container.isVisible =
                    loadState.source.refresh is LoadState.Loading
            }
        }
    }

    override fun initView() = with(binding) {
        rvRepositories.adapter = adapter
        rvRepositories.addItemDecoration(AdaptiveSpacingItemDecoration(18.dp.toInt()))
        llFilter.setOnClickListener {
            val filterSheet =
                FilterBottomSheetFragment.newInstance()
            filterSheet.show(
                childFragmentManager,
                FilterBottomSheetFragment.TAG
            )
        }

    }


    override fun initObservers() {
        collectLatestWhenStarted(viewModel.repositories) {
            adapter.submitData(it)
        }
        collectWhenStarted(viewModel.selectedFilter) {
            binding.tvFilter.setText(it.titleRes)
        }
        collectWhenStarted(viewModel.showNoNetworkView) {
            binding.noNetworkView.container.isVisible = it
            if (!it) adapter.retry()
        }
    }

    override fun onDestroyView() {
        if (::repoDialog.isInitialized) {
            repoDialog.cancel()
        }
        super.onDestroyView()
    }
}