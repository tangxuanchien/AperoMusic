package com.example.chientx_apero.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state: StateFlow<HomeState> = _state
    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    fun processIntent(intent: HomeIntent, context: Context) {
        val current = _state.value
        when (intent) {
            HomeIntent.TogglePlayback -> {}
        }
    }

    private fun sendEvent(event: HomeEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}