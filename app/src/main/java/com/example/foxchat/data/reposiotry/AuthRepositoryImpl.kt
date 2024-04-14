package com.example.foxchat.data.reposiotry

import com.example.foxchat.data.model.AuthModel
import com.example.foxchat.data.model.User
import com.example.foxchat.utils.FireRoutes
import com.example.foxchat.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepositoryImpl : AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val usersRef = FirebaseFirestore.getInstance().collection(FireRoutes.USERS)

    override fun checkUserAuth() = auth.currentUser?.uid != null

    override fun signUp(authModel: AuthModel, onResponse: (Response<Boolean>) -> Unit) {
        val response = Response<Boolean>()

        auth.createUserWithEmailAndPassword(authModel.email, authModel.password)
            .addOnSuccessListener {
                auth.currentUser?.uid?.let { uid ->
                    usersRef.document(uid).set(
                        User(
                            name = authModel.name,
                            email = authModel.email
                        )
                    )
                        .addOnSuccessListener {
                            response.data = true
                            onResponse(response)
                        }
                        .addOnFailureListener { exception ->
                            response.exception = exception
                            onResponse(response)
                        }
                }
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }

    override fun signIn(email: String, password: String, onResponse: (Response<Boolean>) -> Unit) {
        val response = Response<Boolean>()
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                response.data = true
                onResponse(response)
            }
            .addOnFailureListener { exception ->
                response.exception = exception
                onResponse(response)
            }
    }

    override fun signOut(onResponse: (Response<Boolean>) -> Unit) {
        auth.signOut()
        val response = Response(data = !checkUserAuth())
        onResponse(response)
    }
}