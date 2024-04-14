package com.example.foxchat.ui.screens.select_person_screen

import com.example.foxchat.data.model.Person

sealed class SelectPersonEvent {
    data object OnCLickBack : SelectPersonEvent()
    data class OnClickSelectedPerson(val person: Person) : SelectPersonEvent()
    data class OnClickPerson(val person: Person) : SelectPersonEvent()
    data object OnClickContinue: SelectPersonEvent()
    data class OnSearch(val query:String): SelectPersonEvent()
    data class OnQueryChange(val query: String): SelectPersonEvent()
}