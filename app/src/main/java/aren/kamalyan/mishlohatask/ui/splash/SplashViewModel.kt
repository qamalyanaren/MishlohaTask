package aren.kamalyan.mishlohatask.ui.splash

import androidx.lifecycle.viewModelScope
import aren.kamalyan.mishlohatask.common.navigation.Command
import aren.kamalyan.mishlohatask.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    init {
        launchApp()
    }

    private fun launchApp() = viewModelScope.launch {
        delay(SPLASH_DURATION)
        sendCommand(Command.NavCommand(SplashFragmentDirections.actionToMain()))
    }

    companion object {
        const val SPLASH_DURATION = 1000L
    }

}