package aren.kamalyan.domain.delegate

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton


sealed interface OfEvent {
    data class UnauthorizedEvent(val throwable: Throwable? = null) : OfEvent
}

@Singleton
class EventDelegate @Inject constructor() {

    private val _eventChannel = MutableSharedFlow<OfEvent>(
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _eventChannel.asSharedFlow()

    suspend fun postEvent(event: OfEvent) {
        _eventChannel.emit(event)
    }
}