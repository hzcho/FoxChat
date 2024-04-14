package com.example.foxchat.ui.screens.sign_up_screen

sealed class SignUpEvent {
    object OnSignUp: SignUpEvent()
    object OpenSignInScreen: SignUpEvent()
    data class OnNameTextChange(val name:String): SignUpEvent()
    data class OnEmailTextChange(val email:String): SignUpEvent()
    data class OnPasswordTextChange(val password:String): SignUpEvent()
}