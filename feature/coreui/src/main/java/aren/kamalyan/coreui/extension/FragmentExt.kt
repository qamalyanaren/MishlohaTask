package aren.kamalyan.coreui.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun <T> Fragment.collectWhenStarted(flow: Flow<T>, block: suspend (value: T) -> Unit) =
    flow.flowWithLifecycle(lifecycle)
        .onEach(block)
        .launchIn(viewLifecycleScope)

fun <T> Fragment.collectLatestWhenStarted(flow: Flow<T>, block: suspend (value: T) -> Unit) {
    viewLifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle).collectLatest { block(it) }
    }
}

val Fragment.viewLifecycleScope: CoroutineScope get() = viewLifecycleOwner.lifecycleScope

fun Fragment.getColor(@ColorRes resId: Int) = requireContext().getColorCompat(resId)

fun Fragment.getDrawable(@DrawableRes resId: Int) = requireContext().getDrawableCompat(resId)

fun Fragment.getFont(@FontRes resId: Int) = requireContext().getFont(resId)

fun Fragment.showToast(message: CharSequence) = requireContext().showToast(message)
fun Fragment.showToast(@StringRes  resId: Int) = requireContext().showToast(resId)

fun Fragment.openLink(link: String) = requireContext().openLink(link)

fun Fragment.hideKeyboard() {
    val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    val currentFocusedView = activity?.currentFocus

    if (inputMethodManager != null && currentFocusedView != null) {
        // Check if the keyboard is open by verifying if the soft input window is active
        if (inputMethodManager.isAcceptingText) {
            // Hide the keyboard
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.windowToken, 0)
        }
    }
}