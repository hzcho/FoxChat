package com.example.foxchat.utils

sealed class UiEvent {
    data class Navigate(val route:String):UiEvent()
    data class SubNavigate(val route:String):UiEvent()
    data object PopBackStack:UiEvent()
    data class ShowSnackBar(val message:String):UiEvent()
    data object PhotoPick:UiEvent()
}