package com.example.foxchat.ui.navigation

import android.os.Parcelable
import com.example.foxchat.data.model.Person
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PersonsArg(val persons:@RawValue List<Person>):Parcelable