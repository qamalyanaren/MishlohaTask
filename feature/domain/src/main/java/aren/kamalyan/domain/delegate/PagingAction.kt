package aren.kamalyan.domain.delegate

import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow

sealed interface PagingAction {
    class UpdateFavorite(val id: Long, val isFavorite: Boolean) : PagingAction
}

class PagingActionDelegate @Inject constructor() {

    private val _actions = MutableStateFlow<List<PagingAction>>(emptyList())
    val actions = _actions.asStateFlow()

    private val _clearDataChannel = Channel<Unit>(Channel.BUFFERED)
    val clearDataChannel = _clearDataChannel.receiveAsFlow()

    suspend fun clearData() = _clearDataChannel.send(Unit)
    fun act(action: PagingAction) { _actions.value += action }
    fun clearActions() { _actions.value = emptyList() }
}

@OptIn(FlowPreview::class)
fun <T : Any> Flow<PagingData<T>>.withPagingActions(
    pagingActionDelegate: PagingActionDelegate,
    applyAction: (pagingData: PagingData<T>, action: PagingAction) -> PagingData<T>
) = flowOf(
    pagingActionDelegate.clearDataChannel.map { PagingData.empty() },
    this
).flattenMerge().combine(pagingActionDelegate.actions) { pagingData, actions ->
    actions.fold(pagingData) { paging, action ->
        applyAction(paging, action)
    }
}
