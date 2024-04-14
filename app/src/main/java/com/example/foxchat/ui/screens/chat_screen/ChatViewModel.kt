package com.example.foxchat.ui.screens.chat_screen

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.model.Chat
import com.example.foxchat.data.model.Message
import com.example.foxchat.data.model.Person
import com.example.foxchat.data.reposiotry.ChatRepository
import com.example.foxchat.data.reposiotry.PersonRepository
import com.example.foxchat.ui.navigation.ChatArg
import com.example.foxchat.utils.ScreenRoutes
import com.example.foxchat.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val personRepository: PersonRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiEVent = Channel<UiEvent>()
    val uiEvent = _uiEVent.receiveAsFlow()
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    init {// костыль
        val chatArg = savedStateHandle.get<ChatArg>(ScreenRoutes.CHAT_ARG)

        chatArg?.chat?.let { chat ->
            _chatState.value = _chatState.value.copy(chat = chat)

            chat.memberIds?.let { memberIds ->
                personRepository.getPersons(memberIds) { response ->
                    response.data?.let { resMembers ->

                        chatRepository.getMessages(chat.id) { response ->
                            response.data?.let { resMessages ->
                                _chatState.value = _chatState.value.copy(
                                    messages = convertMessages(members = resMembers, messages = resMessages)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: ChatEvent) = with(_chatState) {
        when (event) {
            is ChatEvent.OnCLickBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is ChatEvent.SendMessage -> {
                viewModelScope.launch {
                    chatRepository.sendMessage(
                        chatId = value.chat.id,
                        text = value.messageText
                    ) { response ->

                    }

                    value = value.copy(
                        messageText = ""
                    )
                }
            }

            is ChatEvent.OnMessageChange -> {
                value = value.copy(
                    messageText = event.messageText
                )
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEVent.send(event)
        }
    }

    private fun convertMessages(members: List<Person>, messages: List<Message>): List<MessageState> {
        val messageStates = mutableListOf<MessageState>()
        val timeFormat = SimpleDateFormat("HH:mm")

        members.forEach { member ->
            val memberMessageStates = messages.filter { it.senderId == member.id }.map { message ->
                val time = message.timestamp?.let { timestamp ->
                    val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                    val netDate = Date(milliseconds)
                    timeFormat.format(netDate).toString()
                }
                MessageState(
                    text = message.text,
                    senderName = member.name ?: "unknown",
                    senderImageUrl = member.imageUrl,
                    time = time.toString()
                )
            }

            messageStates.addAll(memberMessageStates)
        }

        messageStates.fastForEachIndexed { i, messageState ->
            Log.e("sisi", i.toString())
        }
        return messageStates
    }
}

data class ChatState(
    val messageText: String = "",
    val chat: Chat = Chat(),
    val messages: List<MessageState> = emptyList()
)

data class MessageState(
    val text: String = "",
    val senderName: String = "",
    val senderImageUrl: String? = null,
    val time: String = ""
)