package aren.kamalyan.coreui.utils

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object KeyboardEventHandler {
    private val _isKeyboardVisible = MutableSharedFlow<Boolean>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val isKeyboardVisible = _isKeyboardVisible.asSharedFlow()

    val keyboardCallback: (isVisible: Boolean) -> Unit = { isVisible ->
        // Only emit if the new value is different from the last emitted value
        if (_isKeyboardVisible.replayCache.firstOrNull() != isVisible) {
            _isKeyboardVisible.tryEmit(isVisible)
        }
    }
}