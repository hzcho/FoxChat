package com.example.foxchat.ui.navigation

import android.os.Parcelable
import com.example.foxchat.data.model.Chat
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ChatArg (
    val chat:@RawValue Chat
):Parcelable