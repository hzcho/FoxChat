package com.example.foxchat.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import com.google.type.Date

@IgnoreExtraProperties
data class Message(
    @DocumentId
    val id:String?=null,
    val senderId:String="",
    val text:String="",
    @ServerTimestamp
    val timestamp: Timestamp?=null
){
    companion object{
        const val TIMESTAMP="timestamp"
    }
}
