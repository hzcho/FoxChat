package com.example.foxchat.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    @DocumentId
    val id:String="",
    val name:String="",
    val email:String="",
    val imageUrl:String?=null
){
    companion object{
        const val NAME="name"
        const val IMAGE_URL="imageUrl"
    }
}
