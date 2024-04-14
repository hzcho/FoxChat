package com.example.foxchat.data.reposiotry

import com.example.foxchat.data.model.Person
import com.example.foxchat.utils.Response

interface PersonRepository {
    fun searchPerson(query:String, onResponse:(Response<List<Person>>)->Unit)
    fun getPerson(id:String, onResponse:(Response<Person>)->Unit)
    fun getPersons(ids:List<String>, onResponse:(Response<List<Person>>)->Unit)
}