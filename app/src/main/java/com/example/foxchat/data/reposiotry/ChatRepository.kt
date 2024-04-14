package com.example.foxchat.data.reposiotry

import android.net.Uri
import com.example.foxchat.data.model.Chat
import com.example.foxchat.data.model.Message
import com.example.foxchat.utils.Response

interface ChatRepository {
    fun getChats(onResponse:(Response<List<Chat>>)->Unit)
    fun createChat(chat:Chat, onResponse: (Response<String>) -> Unit)
    fun setImageChat(chatId:String, imageUri:Uri, onResponse: (Response<Boolean>) -> Unit)
    fun addMember(chatId:String, memberId:String, onResponse: (Response<Boolean>) -> Unit)
    fun removeMember(chatId:String, memberId:String, onResponse: (Response<Boolean>) -> Unit)
    fun getMessages(chatId:String, onResponse: (Response<List<Message>>) -> Unit)
    fun sendMessage(chatId:String, text:String, onResponse: (Response<Boolean>) -> Unit)
}