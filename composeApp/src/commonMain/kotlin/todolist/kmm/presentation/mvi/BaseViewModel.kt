package todolist.kmm.presentation.mvi

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch


abstract class BaseViewModel<Event : UIEvent, State : UIState, Effect : UIEffect> : ScreenModel {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    protected val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    /**
     * Start listening to Event
     */
    private fun subscribeEvents() {
        screenModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }
    /**
     * Handle each event
     */
    abstract fun handleEvent(event: Event)

    /**
     * Set new Event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        screenModelScope.launch { _event.emit(newEvent) }
    }
    /**
     * Set new Ui State
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * Set new Effect
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        screenModelScope.launch { _effect.send(effectValue) }
    }
}

