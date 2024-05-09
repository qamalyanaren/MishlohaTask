package aren.kamalyan.mishlohatask.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aren.kamalyan.mishlohatask.common.navigation.Command
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {

    private val _command by lazy { Channel<Command>(Channel.BUFFERED) }
    val command = _command.receiveAsFlow()

    protected fun sendCommand(command: Command) {
        viewModelScope.launch { _command.send(command) }
    }

    fun navigateUp(nestedLevel: Int = 0) = sendCommand(Command.NavigateUpCommand(nestedLevel))

}
