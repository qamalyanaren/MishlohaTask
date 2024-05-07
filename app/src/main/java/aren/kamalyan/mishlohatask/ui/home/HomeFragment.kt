package aren.kamalyan.mishlohatask.ui.home


import androidx.fragment.app.viewModels
import aren.kamalyan.coreui.delegate.viewBinding
import aren.kamalyan.coreui.extension.collectLatestWhenStarted
import aren.kamalyan.coreui.extension.dp
import aren.kamalyan.coreui.utils.AdaptiveSpacingItemDecoration
import aren.kamalyan.mishlohatask.R
import aren.kamalyan.mishlohatask.common.base.BaseFragment
import aren.kamalyan.mishlohatask.databinding.FragmentHomeBinding
import aren.kamalyan.mishlohatask.ui.home.adapter.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy {
        RepoAdapter(
            onItemClicked = {
//                viewModel.navigateToRepoDetails(it.id)
            }
        )
    }

    override fun initView() = with(binding) {
        rvRepositories.adapter = adapter
        rvRepositories.addItemDecoration(AdaptiveSpacingItemDecoration(18.dp.toInt()))
    }

    override fun initObservers() {
        collectLatestWhenStarted(viewModel.repositories, adapter::submitData)
    }
}