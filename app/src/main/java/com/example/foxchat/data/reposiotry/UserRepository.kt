package com.example.foxchat.data.reposiotry

import android.net.Uri
import com.example.foxchat.data.model.User
import com.example.foxchat.utils.Response

interface UserRepository {
    fun getUser(onResponse:(Response<User>)->Unit)
    fun setImage(imageUri: Uri, onResponse:(Response<Boolean>)->Unit)
    fun setName(name:String, onResponse:(Response<Boolean>)->Unit)
}