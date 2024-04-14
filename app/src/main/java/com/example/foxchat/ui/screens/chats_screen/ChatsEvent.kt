package com.example.foxchat.ui.screens.chats_screen

import com.example.foxchat.data.model.Chat

sealed class ChatsEvent {
    data class OnChatClick(val chat:Chat):ChatsEvent()
}