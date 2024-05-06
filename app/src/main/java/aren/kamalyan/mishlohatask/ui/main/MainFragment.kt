package aren.kamalyan.mishlohatask.ui.main

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import aren.kamalyan.coreui.delegate.viewBinding
import aren.kamalyan.mishlohatask.R
import aren.kamalyan.mishlohatask.common.base.BaseFragment
import aren.kamalyan.mishlohatask.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private lateinit var navController: NavController

    override fun initView() = setupBottomNavigation()

    override fun initObservers() {

    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupBottomNavigation() {
//        val navHostFragment = childFragmentManager.findFragmentById(
//            R.id.mainNavContainer
//        ) as NavHostFragment
//        navController = navHostFragment.navController
//
//        binding.bottomNav.setupWithNavController(navController)
//        binding.bottomNav.setOnItemReselectedListener { item ->
//            // Pop everything up to the reselected item
//            val selectedMenuItemNavGraph = navController.graph.findNode(item.itemId) as? NavGraph
//            selectedMenuItemNavGraph?.let {
//                navController.popBackStack(it.startDestinationId, inclusive = false)
//            }
//        }
    }
}