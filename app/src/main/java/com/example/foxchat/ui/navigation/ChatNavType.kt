package com.example.foxchat.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class ChatNavType: NavType<ChatArg>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ChatArg? {
        return bundle.getParcelable(key) as ChatArg?
    }

    override fun parseValue(value: String): ChatArg {
        return Gson().fromJson(value, ChatArg::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ChatArg) {
        bundle.putParcelable(key, value)
    }
}