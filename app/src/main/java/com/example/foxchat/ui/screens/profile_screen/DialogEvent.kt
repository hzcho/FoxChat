package com.example.foxchat.ui.screens.profile_screen

sealed class DialogEvent {
    data object OnDismiss:DialogEvent()
    data object OnConfirm:DialogEvent()
    data class OnNameChange(val name:String):DialogEvent()
}