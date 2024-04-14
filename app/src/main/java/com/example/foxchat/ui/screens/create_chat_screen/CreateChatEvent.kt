package com.example.foxchat.ui.screens.create_chat_screen

import android.net.Uri

sealed class CreateChatEvent {
    data object OnClickBack: CreateChatEvent()
    data object OnImageClick: CreateChatEvent()
    data class SetImage(val uri: Uri?):CreateChatEvent()
    data class OnNameChange(val chatName:String): CreateChatEvent()
    data object OnChatCreate: CreateChatEvent()
}