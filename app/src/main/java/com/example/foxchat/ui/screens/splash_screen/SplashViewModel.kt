package com.example.foxchat.ui.screens.splash_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.reposiotry.AuthRepository
import com.example.foxchat.utils.ScreenRoutes
import com.example.foxchat.utils.UiEvent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {
    private val _uiEvent=Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()
    private val _splashState= MutableStateFlow(SplashState())
    val splashState=_splashState.asStateFlow()
    private var isAuth=false


    init {
        viewModelScope.launch {
            isAuth=authRepository.checkUserAuth()
        }
    }

    fun onEvent(event: SplashEvent)= with(_splashState){
        when(event){
            is SplashEvent.OnFinishedAnimation ->{
                if(isAuth){
                    sendUiEvent(UiEvent.Navigate(ScreenRoutes.MAIN_SCREEN))
                }else{
                    sendUiEvent(UiEvent.Navigate(ScreenRoutes.SIGN_UP_SCREEN))
                }
            }
            is SplashEvent.StartAnimation ->{
                value=value.copy(isAnimate = true)
            }
        }
    }

    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

data class SplashState(
    val isAnimate:Boolean=false
)