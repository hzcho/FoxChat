package com.example.foxchat.ui.screens.select_person_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxchat.data.model.Person
import com.example.foxchat.data.reposiotry.PersonRepository
import com.example.foxchat.ui.navigation.PersonsArg
import com.example.foxchat.utils.ScreenRoutes
import com.example.foxchat.utils.ScreenRoutes.addArguments
import com.example.foxchat.utils.UiEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(
    private val personRepository:PersonRepository
):ViewModel() {
    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()
    private val _selectPersonState= MutableStateFlow(SelectPersonState())
    val selectPersonState=_selectPersonState.asStateFlow()

    fun onEvent(event: SelectPersonEvent)= with(_selectPersonState){
        when(event){
            is SelectPersonEvent.OnClickSelectedPerson ->{
                value=value.copy(selectedPersons = value.selectedPersons-event.person)
            }
            is SelectPersonEvent.OnClickPerson ->{
                value=value.copy(selectedPersons = value.selectedPersons+event.person)
            }
            is SelectPersonEvent.OnClickContinue ->{
                val navArg= PersonsArg(value.selectedPersons)
                val json= Gson().toJson(navArg)
                sendUiEvent(
                    UiEvent.Navigate(
                        ScreenRoutes.CREATE_CHAT_SCREEN.addArguments(
                            mapOf(ScreenRoutes.PERSONS_ARG to json.toString())
                        )
                    )
                )
            }
            is SelectPersonEvent.OnCLickBack ->{
                sendUiEvent(UiEvent.PopBackStack)
            }
            is SelectPersonEvent.OnQueryChange ->{
                value=value.copy(query = event.query)
            }
            is SelectPersonEvent.OnSearch ->{
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

data class SelectPersonState(
    val query:String="",
    val selectedPersons:List<Person> = emptyList(),
    val persons:List<Person> = emptyList()
)