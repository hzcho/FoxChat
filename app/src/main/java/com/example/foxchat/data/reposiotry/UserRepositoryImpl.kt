package com.example.foxchat.data.reposiotry

import android.net.Uri
import com.example.foxchat.data.model.User
import com.example.foxchat.utils.FireRoutes
import com.example.foxchat.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage

class UserRepositoryImpl : UserRepository {
    private val userUid = FirebaseAuth.getInstance().currentUser?.uid!!
    private val userRef = FirebaseFirestore.getInstance().collection(FireRoutes.USERS).document(userUid)
    private val storage=FirebaseStorage.getInstance().reference.child(FireRoutes.IMAGES).child(userUid)

    override fun getUser(onResponse: (Response<User>) -> Unit) {
        val response=Response<User>()
        userRef.get()
            .addOnSuccessListener { docSnapshot ->
                response.data = docSnapshot.toObject<User>()
                onResponse(response)
            }
            .addOnFailureListener {exception->
                response.exception=exception
                onResponse(response)
            }
    }

    override fun setImage(imageUri: Uri, onResponse: (Response<Boolean>) -> Unit) {
        val response = Response<Boolean>()

        storage.putFile(imageUri)
            .addOnSuccessListener {
                storage.downloadUrl
                    .addOnSuccessListener { downloadUri ->
                        userRef.update(User.IMAGE_URL, downloadUri.toString())
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


    override fun setName(name: String, onResponse: (Response<Boolean>) -> Unit) {
        val response=Response<Boolean>()
        userRef.update(User.NAME, name)
            .addOnSuccessListener {
                response.data = true
                onResponse(response)
            }
            .addOnFailureListener {
                response.data = false
                onResponse(response)
            }
    }
}