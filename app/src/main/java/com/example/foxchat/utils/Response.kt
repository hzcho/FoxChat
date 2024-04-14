package com.example.foxchat.utils

import java.lang.Exception

data class Response<T> (
    var data:T?=null,
    var exception: Exception?=null
)