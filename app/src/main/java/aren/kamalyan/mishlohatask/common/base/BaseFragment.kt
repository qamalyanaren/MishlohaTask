package aren.kamalyan.mishlohatask.common.base


import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import app.shiva.vpn.common.navigation.Command
import aren.kamalyan.coreui.extension.addSystemPadding
import aren.kamalyan.coreui.extension.collectWhenStarted
import aren.kamalyan.coreui.extension.hideKeyboard
import aren.kamalyan.mishlohatask.common.extension.navigateSafe
import aren.kamalyan.mishlohatask.utils.manager.LightSystemBarsManager
import aren.kamalyan.mishlohatask.utils.manager.system_padding.SystemPaddingParams

abstract class BaseFragment<VM>(@LayoutRes layout: Int) :
    Fragment(layout) where VM : BaseViewModel {

    abstract val viewModel: VM
    open val applySystemPaddings: Boolean = true
    open val systemPaddingParams: SystemPaddingParams = SystemPaddingParams()
    open val isLightStatusBar = false
    open val isLightNavBar = false

    private var navController: NavController? = null
    protected open val navControllerRes: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applySystemPaddings(view)
        applyLightStatusBar()
        applyLightNavBar()
        navController = getNavController()
        collectWhenStarted(viewModel.command, ::processCommand)
        initView()
        initObservers()
    }

    protected open fun initView() {}
    protected open fun initObservers() {}

    protected open fun processCommand(command: Command) {
        when (command) {
            is Command.FinishAppCommand -> activity?.finishAffinity()
            is Command.NavigateUpCommand -> {
                if (command.nestedLevel > 0)
                    getParentNavController(command.nestedLevel)?.navigateUp()
                else
                    navController?.navigateUp()
            }

            is Command.NavCommand -> {
                if (command.nestedLevel > 0)
                    getParentNavController(command.nestedLevel)?.navigateSafe(command.navDirections)
                else
                    navController?.navigateSafe(command.navDirections)
            }
        }
    }

    private fun getNavController() = try {
        navControllerRes?.let { resId ->
            Navigation.findNavController(requireActivity(), resId)
        } ?: findNavController()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    private fun getParentNavController(level: Int): NavController? {
        var currentLevel = level
        var parentFragment = parentFragment?.parentFragment
        while (currentLevel > 0) {
            parentFragment = parentFragment?.parentFragment
            --currentLevel
        }
        return parentFragment?.parentFragmentManager?.primaryNavigationFragment?.findNavController()
    }

    private fun applySystemPaddings(view: View) {
        if (!applySystemPaddings) return
        view.addSystemPadding(
            isTop = systemPaddingParams.isTop,
            isBottom = systemPaddingParams.isBottom,
            isIncludeIme = systemPaddingParams.isIncludeIme,
        )
    }

    private fun applyLightStatusBar() {
        val manager = activity as? LightSystemBarsManager ?: return
        manager.setUseLightStatusBar(isLightStatusBar)
    }

    private fun applyLightNavBar() {
        val manager = activity as? LightSystemBarsManager ?: return
        manager.setUseLightNavBar(isLightNavBar)
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }
}
