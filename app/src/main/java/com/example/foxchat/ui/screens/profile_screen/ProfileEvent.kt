package com.example.foxchat.ui.screens.profile_screen

import android.net.Uri

sealed class ProfileEvent {
    data object OnImageClick: ProfileEvent()
    data class SetImage(val uri: Uri?):ProfileEvent()
    data object OnNameClick: ProfileEvent()
    data object SignOut: ProfileEvent()
}