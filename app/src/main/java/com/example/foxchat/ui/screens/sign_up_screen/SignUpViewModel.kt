package com.example.foxchat.ui.screens.sign_up_screen

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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpEvent = _signUpState.asStateFlow()

    fun onEvent(event: SignUpEvent) = with(_signUpState) {
        when (event) {
            is SignUpEvent.OnSignUp -> {
                if (value.name.isEmpty() || value.email.isEmpty() || value.password.isEmpty()) {
                    sendUiEvent(UiEvent.ShowSnackBar("Заполните все поля"))
                    return@with
                }
                authRepository.signUp(
                    AuthModel(
                        name = value.name,
                        email = value.email,
                        password = value.password
                    )
                ) { response ->
                    if(response.data == true){
                        sendUiEvent(UiEvent.Navigate(ScreenRoutes.MAIN_SCREEN))
                    }
                    else if (response.exception != null){
                        sendUiEvent(UiEvent.ShowSnackBar(response.exception?.message ?: "unknown"))
                    }
                }
            }

            is SignUpEvent.OpenSignInScreen -> {
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.SIGN_IN_SCREEN))
            }

            is SignUpEvent.OnNameTextChange -> {
                value = value.copy(name = event.name)
            }

            is SignUpEvent.OnEmailTextChange -> {
                value = value.copy(email = event.email)
            }

            is SignUpEvent.OnPasswordTextChange -> {
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

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = ""
)