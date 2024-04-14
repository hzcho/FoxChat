package com.example.foxchat.ui.screens.search_screen

sealed class SearchEvent {
    data class OnQueryChange(val query:String): SearchEvent()
    data class OnSearch(val query:String): SearchEvent()
}