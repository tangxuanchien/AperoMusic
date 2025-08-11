package com.example.chientx_apero.ui.home

import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class HomeState(
    var currentTheme: ThemeData = darkTheme,
)

sealed interface HomeIntent {
    data object TogglePlayback : HomeIntent
}

sealed interface HomeEvent {
    data class showHomeEvent(val message: String): HomeEvent
}