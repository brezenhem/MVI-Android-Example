package com.mada.softpos.core.arch

import com.airbnb.mvrx.MavericksViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class MviViewModel<Event : MviEvent, Effect : MviEffect, State : MviState>(state: State) :
    MavericksViewModel<State>(state) {

    private val TAG = javaClass.simpleName

    private val eventChannel = Channel<Event>(capacity = Channel.UNLIMITED)
    private val eventFlow: Flow<Event> = eventChannel
        .receiveAsFlow()
        .onEach { Timber.d(TAG, "EVENT -> $it") }

    private val effectChannel = Channel<Effect>(capacity = Channel.UNLIMITED)
    val effectFlow: Flow<Effect> = effectChannel
        .receiveAsFlow()
        .onEach { Timber.d(TAG, "EFFECT -> $it") }

    init {
        observeEvents()
        observeStates()
    }

    protected abstract fun handleEvent(event: Event)

    fun setEvent(event: Event) {
        eventChannel.trySend(event)
    }

    fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        effectChannel.trySend(effectValue)
    }

    private fun observeEvents() {
        viewModelScope.launch {
            eventFlow.collect { handleEvent(it) }
        }
    }

    private fun observeStates() {
        viewModelScope.launch {
            stateFlow.collect { Timber.d(TAG, "STATE -> $it") }
        }
    }
}