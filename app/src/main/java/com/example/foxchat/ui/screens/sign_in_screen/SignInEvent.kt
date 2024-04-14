package com.example.foxchat.ui.screens.sign_in_screen

sealed class SignInEvent {
    object OnSignIn: SignInEvent()
    object OpenSignUpScreen: SignInEvent()
    data class OnEmailTextChange(val email:String): SignInEvent()
    data class OnPasswordTextChange(val password:String): SignInEvent()
}