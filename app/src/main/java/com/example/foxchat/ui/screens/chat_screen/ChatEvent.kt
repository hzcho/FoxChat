package com.example.foxchat.ui.screens.chat_screen

sealed class ChatEvent {
    data class OnMessageChange(val messageText: String): ChatEvent()
    data object SendMessage: ChatEvent()
    data object OnCLickBack: ChatEvent()
}