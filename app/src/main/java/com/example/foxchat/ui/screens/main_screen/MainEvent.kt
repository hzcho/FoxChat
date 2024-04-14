package com.example.foxchat.ui.screens.main_screen

sealed class MainEvent {
    object OnClickFAB: MainEvent()
    data class Navigate(val route:String): MainEvent()
}