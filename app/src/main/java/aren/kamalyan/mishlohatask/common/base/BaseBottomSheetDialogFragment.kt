package aren.kamalyan.mishlohatask.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import app.shiva.vpn.common.navigation.Command
import aren.kamalyan.coreui.extension.collectWhenStarted
import aren.kamalyan.coreui.extension.hideKeyboard
import aren.kamalyan.mishlohatask.common.extension.navigateSafe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding, VM : BaseViewModel> :
    BottomSheetDialogFragment() {

    abstract val viewModel: VM
    abstract val layoutId: Int

    private var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        collectWhenStarted(viewModel.command) { processCommand(it) }
        initView()
        initObservers()
    }

    open fun initView() {}
    open fun initObservers() {}

    protected open fun processCommand(command: Command) {
        when (command) {
            is Command.FinishAppCommand -> activity?.finishAffinity()
            is Command.NavigateUpCommand -> navController?.popBackStack()
            is Command.NavCommand -> navController?.navigateSafe(command.navDirections)
        }
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }
}