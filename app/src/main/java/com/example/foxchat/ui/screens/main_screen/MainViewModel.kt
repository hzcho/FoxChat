package com.example.foxchat.ui.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.utils.ScreenRoutes
import com.example.foxchat.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel :ViewModel() {
    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()
    private val _mainState= MutableStateFlow(MainState())
    val mainState=_mainState.asStateFlow()

    fun onEvent(event: MainEvent)= with(_mainState){
        when(event){
            is MainEvent.OnClickFAB ->{
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.SELECT_PERSON_SCREEN))
            }
            is MainEvent.Navigate ->{
                value=value.copy(
                    isShowFAB = event.route == ScreenRoutes.CHATS_SCREEN,
                    currentRoute = event.route
                )
                sendUiEvent(UiEvent.SubNavigate(event.route))
            }
        }
    }
    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

data class MainState(
    val isShowFAB:Boolean=true,
    val currentRoute:String=""
)