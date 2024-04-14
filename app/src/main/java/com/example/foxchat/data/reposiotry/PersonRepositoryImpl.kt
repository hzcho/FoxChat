package com.example.foxchat.data.reposiotry

import com.example.foxchat.data.model.Person
import com.example.foxchat.utils.FireRoutes
import com.example.foxchat.utils.Response
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class PersonRepositoryImpl:PersonRepository {
    private val usersRef=FirebaseFirestore.getInstance().collection(FireRoutes.USERS)

    override fun searchPerson(query: String, onResponse: (Response<List<Person>>) -> Unit) {
        val response=Response<List<Person>>()

        usersRef.orderBy(Person.NAME).startAt(query).endAt(query+"\uf8ff").get()
            .addOnSuccessListener { snapshot->
                response.data=snapshot.documents.mapNotNull { docSnapshot->
                    docSnapshot.toObject(Person::class.java)
                }
                onResponse(response)
            }
            .addOnFailureListener { exception->
                response.exception=exception
                onResponse(response)
            }
    }

    override fun getPerson(id: String, onResponse: (Response<Person>) -> Unit) {
        val response=Response<Person>()

        usersRef.document(id).get()
            .addOnSuccessListener { docSnapshot->
                response.data=docSnapshot.toObject<Person>()
                onResponse(response)
            }
            .addOnFailureListener { exception->
                response.exception=exception
                onResponse(response)
            }
    }

    override fun getPersons(ids: List<String>, onResponse: (Response<List<Person>>) -> Unit) {
        val response=Response<List<Person>>()

        usersRef.whereIn(FieldPath.documentId(), ids).get()
            .addOnSuccessListener { snapshot->
                response.data=snapshot.documents.mapNotNull { docSnapshot->
                    docSnapshot.toObject(Person::class.java)
                }
                onResponse(response)
            }
            .addOnFailureListener { exception->
                response.exception=exception
                onResponse(response)
            }
    }
}