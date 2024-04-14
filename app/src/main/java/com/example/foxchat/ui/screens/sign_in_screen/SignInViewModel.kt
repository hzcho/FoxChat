package com.example.foxchat.ui.screens.sign_in_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.model.AuthModel
import com.example.foxchat.data.reposiotry.AuthRepository
import com.example.foxchat.utils.ScreenRoutes
import com.example.foxchat.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    fun onEvent(event: SignInEvent) = with(_signInState) {
        when (event) {
            is SignInEvent.OnSignIn -> {
                if (value.email.isEmpty() || value.password.isEmpty()) {
                    sendUiEvent(UiEvent.ShowSnackBar(message = "заполните все поля"))
                }
                authRepository.signIn(
                    email = value.email,
                    password = value.password
                ) { response ->
                    if (response.data == true) {
                        sendUiEvent(UiEvent.Navigate(ScreenRoutes.MAIN_SCREEN))
                    } else if (response.exception != null) {
                        sendUiEvent(UiEvent.ShowSnackBar(response.exception?.message ?: "unknown"))
                    }
                }
            }

            is SignInEvent.OpenSignUpScreen -> {
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.SIGN_UP_SCREEN))
            }

            is SignInEvent.OnEmailTextChange -> {
                value = value.copy(email = event.email)
            }

            is SignInEvent.OnPasswordTextChange -> {
                value = value.copy(password = event.password)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

data class SignInState(
    val email: String = "",
    val password: String = ""
)