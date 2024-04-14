package com.example.foxchat.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class PersonNavType:NavType<PersonsArg>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PersonsArg? {
        return bundle.getParcelable(key) as PersonsArg?
    }

    override fun parseValue(value: String): PersonsArg {
        return Gson().fromJson(value, PersonsArg::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: PersonsArg) {
        bundle.putParcelable(key, value)
    }
}