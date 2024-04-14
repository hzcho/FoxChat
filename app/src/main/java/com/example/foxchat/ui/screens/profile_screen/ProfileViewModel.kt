package com.example.foxchat.ui.screens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.model.User
import com.example.foxchat.data.reposiotry.AuthRepository
import com.example.foxchat.data.reposiotry.UserRepository
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
class ProfileViewModel @Inject constructor(
    private val authRepository:AuthRepository,
    private val userRepository:UserRepository
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()
    private val _dialogState= MutableStateFlow(DialogState())
    val dialogState=_dialogState.asStateFlow()

    init {
        getUser()
    }

    fun onEvent(event: ProfileEvent) = with(_profileState) {
        when (event) {
            is ProfileEvent.OnImageClick -> {
                sendUiEvent(UiEvent.PhotoPick)
            }
            is ProfileEvent.SetImage->{
                event.uri?.let { uri->
                    userRepository.setImage(uri){response->
                        if (response.data == true){
                            getUser()
                        }
                    }
                }
            }
            is ProfileEvent.OnNameClick ->{
                value=value.copy(isShowDialog = true)
            }
            is ProfileEvent.SignOut -> {
                authRepository.signOut { response->
                    if(response.data==true){
                        sendUiEvent(UiEvent.Navigate(ScreenRoutes.SPLASH_SCREEN))
                    }
                }
            }
        }
    }

    fun onDialogEvent(event:DialogEvent)= with(_dialogState){
        when(event){
            is DialogEvent.OnConfirm->{
                userRepository.setName(value.name){response->
                    if (response.data == true){
                        getUser()
                    }
                }
                _profileState.value=_profileState.value.copy(isShowDialog = false)
            }
            is DialogEvent.OnDismiss->{
                value=value.copy(name = "")
                _profileState.value=_profileState.value.copy(isShowDialog = false)
            }
            is DialogEvent.OnNameChange->{
                value=value.copy(name = event.name)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun getUser(){
        userRepository.getUser { response->
            response.data?.let { user->
                _profileState.value=_profileState.value.copy(
                    user=user
                )
            }
        }
    }
}

data class ProfileState(
    val user:User=User(),
    val isShowDialog:Boolean=false,
)

data class DialogState(
    val name:String=""
)