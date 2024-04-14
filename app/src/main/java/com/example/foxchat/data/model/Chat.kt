package com.example.foxchat.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class Chat(
    @DocumentId
    val id:String = "",
    val name: String = "",
    val imageUrl:String?=null,
    val memberIds: List<String>? = null,
    @ServerTimestamp
    val lastUpdatedAt:Timestamp?=null,
    val lastMessage: Message?=null,
){
    companion object{
        const val IMAGE_URL="imageUrl"
        const val MEMBER_IDS="memberIds"
        const val MESSAGES="messages"
        const val LAST_UPDATED_AT="lastUpdatedAt"
        const val LAST_MESSAGE="lastMessage"
    }
}
