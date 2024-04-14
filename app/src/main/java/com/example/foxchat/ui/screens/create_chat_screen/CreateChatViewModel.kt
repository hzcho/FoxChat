package com.example.foxchat.ui.screens.create_chat_screen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.model.Chat
import com.example.foxchat.data.model.Person
import com.example.foxchat.data.reposiotry.ChatRepository
import com.example.foxchat.ui.navigation.PersonsArg
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
class CreateChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _createChatState = MutableStateFlow(CreateChatState())
    val createChatState = _createChatState.asStateFlow()

    init {
        val selectedPerson=savedStateHandle.get<PersonsArg>(ScreenRoutes.PERSONS_ARG)?.persons
        selectedPerson?.let {
            _createChatState.value=_createChatState.value.copy(selectedPersons = selectedPerson)
        }
    }
    fun onEvent(event: CreateChatEvent) = with(_createChatState) {
        when (event) {
            is CreateChatEvent.OnClickBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CreateChatEvent.OnNameChange -> {
                value = value.copy(chatName = event.chatName)
            }
            is CreateChatEvent.OnImageClick -> {
                sendUiEvent(UiEvent.PhotoPick)
            }
            is CreateChatEvent.SetImage->{
                value=value.copy(
                    imageUri = event.uri
                )
            }
            is CreateChatEvent.OnChatCreate -> {
                if (value.chatName.isEmpty()) {
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = "the chat name should not be empty"
                        )
                    )
                    return@with
                }
                chatRepository.createChat(
                    Chat(
                        name = value.chatName,
                        memberIds = value.selectedPersons.mapNotNull { it.id }
                    )
                ){response->
                    val chatId=response.data

                    if (chatId != null && value.imageUri != null){
                        chatRepository.setImageChat(chatId, value.imageUri!!){

                        }
                    }
                }
                sendUiEvent(UiEvent.Navigate(ScreenRoutes.MAIN_SCREEN))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

data class CreateChatState(
    val chatName: String = "",
    val imageUri: Uri?=null,
    val selectedPersons: List<Person> = emptyList()
)