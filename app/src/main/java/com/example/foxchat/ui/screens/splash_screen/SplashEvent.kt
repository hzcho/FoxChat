package com.example.foxchat.ui.screens.splash_screen

sealed class SplashEvent {
    object OnFinishedAnimation: SplashEvent()
    object StartAnimation: SplashEvent()
}