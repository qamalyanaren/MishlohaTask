package aren.kamalyan.mishlohatask.ui.favorite


import androidx.fragment.app.viewModels
import aren.kamalyan.coreui.delegate.viewBinding
import aren.kamalyan.coreui.extension.collectWhenStarted
import aren.kamalyan.coreui.extension.dp
import aren.kamalyan.coreui.utils.AdaptiveSpacingItemDecoration
import aren.kamalyan.mishlohatask.R
import aren.kamalyan.mishlohatask.common.base.BaseFragment
import aren.kamalyan.mishlohatask.databinding.FragmentFavoriteBinding
import aren.kamalyan.mishlohatask.ui.details.RepoDetailDialog
import aren.kamalyan.mishlohatask.ui.home.adapter.RepoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel>(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private lateinit var repoDialog: RepoDetailDialog

    override val viewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy {
        RepoListAdapter(
            onItemClicked = { repo ->
                repoDialog =
                    RepoDetailDialog(
                        this,
                        repo
                    );
                repoDialog.show()
            },
            onFavoriteClicked = viewModel::removeFromFavorite
        )
    }

    override fun initView() = with(binding) {
        rvFavorite.adapter = adapter
        rvFavorite.addItemDecoration(AdaptiveSpacingItemDecoration(18.dp.toInt()))
    }

    override fun initObservers() {
        collectWhenStarted(viewModel.repos, adapter::submitList)
    }

    override fun onDestroyView() {
        if (::repoDialog.isInitialized) {
            repoDialog.cancel()
        }
        super.onDestroyView()
    }
}