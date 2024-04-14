package com.example.foxchat.ui.screens.chats_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.model.Chat
import com.example.foxchat.data.reposiotry.ChatRepository
import com.example.foxchat.ui.navigation.ChatArg
import com.example.foxchat.ui.navigation.PersonsArg
import com.example.foxchat.utils.ScreenRoutes
import com.example.foxchat.utils.ScreenRoutes.addArguments
import com.example.foxchat.utils.UiEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _chatsState = MutableStateFlow(ChatsState())
    val chatsState = _chatsState.asStateFlow()

    init {
        chatRepository.getChats { response ->
            response.data?.let { chats ->
                _chatsState.value = _chatsState.value.copy(
                    chats = chats
                )
            }
        }
    }

    fun onEvent(event: ChatsEvent) = with(_chatsState) {
        when (event) {
            is ChatsEvent.OnChatClick -> {
                val chatArg = ChatArg(event.chat)
                val json = Gson().toJson(chatArg)
                sendUiEvent(
                    UiEvent.Navigate(
                        ScreenRoutes.CHAT_SCREEN.addArguments(
                            mapOf(ScreenRoutes.CHAT_ARG to json)
                        )
                    )
                )
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

data class ChatsState(
    val chats: List<Chat> = emptyList()
)