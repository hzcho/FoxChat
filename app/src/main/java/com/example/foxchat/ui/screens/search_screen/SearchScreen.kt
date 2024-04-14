package com.example.foxchat.ui.screens.search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foxchat.data.model.Person
import com.example.foxchat.ui.commom_ui_elements.PersonItem

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = null){
        viewModel.uiEvent.collect{event->
            when(event){
                else->{}
            }
        }
    }

    SearchBody(
        state = viewModel.searchState.collectAsState().value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SearchBody(
    state: SearchState,
    onEvent:(SearchEvent)->Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchTopBar(
            query = state.query,
            onQueryChange = {newQuery->
                onEvent(SearchEvent.OnQueryChange(newQuery))
            },
            onSearch = {query->
                onEvent(SearchEvent.OnSearch(query))
            },
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end=8.dp, top = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp)
        ){
            items(state.persons){person->
                PersonItem(
                    person = person
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview(){
    SearchBody(
        state = SearchState(
            persons = listOf(
                Person(
                    name = "labadu",
                    imageUrl = ""
                )
            )
        ),
        onEvent = {}
    )
}