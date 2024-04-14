package com.example.foxchat.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Person(
    @DocumentId
    val id:String?=null,
    val name:String?=null,
    val imageUrl:String?=null,
){
    companion object{
        val NAME="name"
    }
}
