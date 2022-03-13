package com.x.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : BaseEvent, State : BaseState> : ViewModel() {

    abstract fun initState(): State
    abstract fun handleEvent(event: Event)

    private val _initState: State by lazy { initState() }
    private val _state: MutableStateFlow<State> = MutableStateFlow(_initState)

    private val _eventChannel: Channel<Event> = Channel()
    private val eventChannel = _eventChannel.receiveAsFlow()

    val state: MutableStateFlow<State> = _state

    init {
        initEvent()
    }

    private fun initEvent() {
        viewModelScope.launch {
            eventChannel.collect { event ->
                handleEvent(event = event)
            }
        }
    }

    fun publish(event: Event) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}