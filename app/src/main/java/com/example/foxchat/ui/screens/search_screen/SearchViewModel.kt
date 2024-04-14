package com.example.foxchat.ui.screens.search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.model.Person
import com.example.foxchat.data.reposiotry.PersonRepository
import com.example.foxchat.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val personRepository: PersonRepository
): ViewModel() {
    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()
    private val _searchState= MutableStateFlow(SearchState())
    val searchState=_searchState.asStateFlow()

    fun onEvent(event: SearchEvent)= with(_searchState){
        when(event){
            is SearchEvent.OnQueryChange -> {
                value=value.copy(
                    query = event.query
                )
            }
            is SearchEvent.OnSearch -> {
                personRepository.searchPerson(event.query){response->
                    response.data?.let {persons->
                        value=value.copy(persons = persons)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

data class SearchState(
    val query:String="",
    val persons:List<Person> = emptyList()
)