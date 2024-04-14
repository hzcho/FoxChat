package com.example.foxchat.data.reposiotry

import android.net.Uri
import android.util.Log
import com.example.foxchat.data.model.Chat
import com.example.foxchat.data.model.Message
import com.example.foxchat.data.model.User
import com.example.foxchat.utils.FireRoutes
import com.example.foxchat.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ChatRepositoryImpl : ChatRepository {
    private val userUid = FirebaseAuth.getInstance().currentUser?.uid!!
    private val chatsRef = FirebaseFirestore.getInstance().collection(FireRoutes.CHATS)
    private val storage = FirebaseStorage.getInstance().reference.child(FireRoutes.IMAGES)

    override fun getChats(onResponse: (Response<List<Chat>>) -> Unit) {
        val response = Response<List<Chat>>()

        chatsRef.whereArrayContains(Chat.MEMBER_IDS, userUid).get()
            .addOnSuccessListener { snapshot ->
                response.data = snapshot.documents.mapNotNull { docSnapshot ->
                    docSnapshot.toObject(Chat::class.java)
                }
                onResponse(response)
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }

    override fun createChat(chat: Chat, onResponse: (Response<String>) -> Unit) {
        val response = Response<String>()
        val chatWithUser = chat.copy(
            memberIds = chat.memberIds?.plus(userUid)
        )

        chatsRef.add(chatWithUser)
            .addOnSuccessListener { docSnapshot ->
                response.data = docSnapshot.id
                onResponse(response)
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }

    override fun setImageChat(chatId: String, imageUri: Uri, onResponse: (Response<Boolean>) -> Unit) {
        val response = Response<Boolean>()

        storage.child(chatId).putFile(imageUri)
            .addOnSuccessListener {
                storage.child(chatId).downloadUrl
                    .addOnSuccessListener { downloadUri ->
                        chatsRef.document(chatId).update(Chat.IMAGE_URL, downloadUri.toString())
                            .addOnSuccessListener {
                                response.data = true
                                onResponse(response)
                            }
                            .addOnFailureListener { exception ->
                                response.exception = exception
                                onResponse(response)
                            }
                    }
                    .addOnFailureListener { exception ->
                        response.exception = exception
                        onResponse(response)
                    }
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }

    override fun addMember(chatId: String, memberId: String, onResponse: (Response<Boolean>) -> Unit) {
        val response = Response<Boolean>()

        chatsRef.document(chatId).update("memberIds", FieldValue.arrayUnion(memberId))
            .addOnSuccessListener {
                response.data = true
                onResponse(response)
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }

    override fun removeMember(chatId: String, memberId: String, onResponse: (Response<Boolean>) -> Unit) {
        val response = Response<Boolean>()

        chatsRef.document(chatId).update("memberIds", FieldValue.arrayRemove(memberId))
            .addOnSuccessListener {
                response.data = true
                onResponse(response)
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }

    override fun getMessages(chatId: String, onResponse: (Response<List<Message>>) -> Unit) {
        val response = Response<List<Message>>()

        chatsRef.document(chatId).collection(Chat.MESSAGES).orderBy(Message.TIMESTAMP)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    response.exception = e
                    onResponse(response)
                }

                if (snapshot != null) {
                    response.data = snapshot.documents.mapNotNull { docSnapshot ->
                        docSnapshot.toObject(Message::class.java)
                    }
                    onResponse(response)
                }
            }
    }

    override fun sendMessage(chatId: String, text: String, onResponse: (Response<Boolean>) -> Unit) {
        val message = Message(
            text = text,
            senderId = userUid
        )
        val response = Response<Boolean>()

        chatsRef.document(chatId).collection(Chat.MESSAGES).add(message)
            .addOnSuccessListener {
                chatsRef.document(chatId).update(
                    mapOf(
                        Chat.LAST_MESSAGE to message,
                        Chat.LAST_UPDATED_AT to null
                    )
                ).addOnSuccessListener {
                    response.data = true
                    onResponse(response)
                }
                    .addOnFailureListener { exception ->
                        response.exception = exception
                        onResponse(response)
                    }
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }
}