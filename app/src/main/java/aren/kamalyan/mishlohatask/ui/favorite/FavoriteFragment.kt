package aren.kamalyan.mishlohatask.ui.favorite


import androidx.fragment.app.viewModels
import aren.kamalyan.coreui.delegate.viewBinding
import aren.kamalyan.mishlohatask.R
import aren.kamalyan.mishlohatask.common.base.BaseFragment
import aren.kamalyan.mishlohatask.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel>(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override val viewModel: FavoriteViewModel by viewModels()

    override fun initView() {

    }

    override fun initObservers() {

    }
}