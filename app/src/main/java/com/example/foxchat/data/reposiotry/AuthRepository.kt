package com.example.foxchat.data.reposiotry

import com.example.foxchat.data.model.AuthModel
import com.example.foxchat.utils.Response

interface AuthRepository {
    fun checkUserAuth():Boolean
    fun signUp(authModel: AuthModel, onResponse:(Response<Boolean>)->Unit)
    fun signIn(email:String, password:String, onResponse:(Response<Boolean>)->Unit)
    fun signOut(onResponse:(Response<Boolean>)->Unit)
}