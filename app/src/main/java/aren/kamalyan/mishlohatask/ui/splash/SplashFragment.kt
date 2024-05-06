package aren.kamalyan.mishlohatask.ui.splash


import androidx.fragment.app.viewModels
import aren.kamalyan.coreui.delegate.viewBinding
import aren.kamalyan.mishlohatask.R
import aren.kamalyan.mishlohatask.common.base.BaseFragment
import aren.kamalyan.mishlohatask.databinding.FragmentSplashBinding
import aren.kamalyan.mishlohatask.utils.manager.system_padding.SystemPaddingParams
import dagger.hilt.android.AndroidEntryPoint
import java.time.Year

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override val viewModel: SplashViewModel by viewModels()
    override val systemPaddingParams: SystemPaddingParams =
        super.systemPaddingParams.copy(isTop = false)
    override val isLightNavBar: Boolean = false
    override val isLightStatusBar: Boolean = false
}