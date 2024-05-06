package aren.kamalyan.coreui.extension


import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import aren.kamalyan.coreui.utils.KeyboardEventHandler

/**
 * Adds system and IME padding to the target view based on visibility and configuration.
 *
 * @param targetView The view to which the padding will be applied. Default is the view itself.
 * @param isTop If true, apply top padding based on system bars.
 * @param isBottom If true, apply bottom padding based on system bars or keyboard if visible.
 * @param isIncludeIme If true, consider the IME (keyboard) in calculating padding.
 */
fun View.addSystemPadding(
    targetView: View = this,
    isTop: Boolean = true,
    isBottom: Boolean = true,
    isIncludeIme: Boolean = true,
) {
    doOnApplyWindowInsets { _, windowInsets, initialPadding ->
        val mask =
            WindowInsetsCompat.Type.systemBars() or (if (isIncludeIme) WindowInsetsCompat.Type.ime() else 0)


        val isKeyboardVisible = ViewCompat.getRootWindowInsets(targetView)
            ?.isVisible(WindowInsetsCompat.Type.ime()) == true

        KeyboardEventHandler.keyboardCallback(isKeyboardVisible)
        val insets = windowInsets.getInsets(mask)
        targetView.updatePadding(
            top = initialPadding.top + if (isTop) insets.top else 0,
            bottom = initialPadding.bottom + if (isBottom || isKeyboardVisible) insets.bottom else 0,
        )
    }
}

/**
 * Executes a block of code when WindowInsets are applied to the view.
 *
 * @param block The block to execute, providing the view, the current insets, and the view's initial padding.
 */
fun View.doOnApplyWindowInsets(block: (v: View, insets: WindowInsetsCompat, initialPadding: Rect) -> Unit) {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
        WindowInsetsCompat.CONSUMED
    }
    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)


/**
 * Requests window insets when the view is attached to the window.
 */
private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(v)
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}
