package com.example.foxchat.ui.screens.select_person_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.foxchat.data.model.Person
import com.example.foxchat.ui.commom_ui_elements.PersonItem
import com.example.foxchat.utils.UiEvent

@Composable
fun SelectPersonScreen(
    navController: NavHostController,
    viewModel: CreateChatViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = null) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                is UiEvent.PopBackStack -> {
                    navController.popBackStack()
                }

                else -> {}
            }
        }
    }

    SelectPersonBody(
        state = viewModel.selectPersonState.collectAsState().value,
        onEvent = viewModel::onEvent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectPersonBody(
    state: SelectPersonState,
    onEvent: (SelectPersonEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                query = state.query,
                onQueryChange = { newQuery ->
                    onEvent(SelectPersonEvent.OnQueryChange(newQuery))
                },
                onSearch = { query ->
                    onEvent(SelectPersonEvent.OnSearch(query))
                },
                onClickBack = {
                    onEvent(SelectPersonEvent.OnCLickBack)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            SelectedContainer(
                selectedPersons = state.selectedPersons,
                modifier = Modifier.fillMaxWidth().height(80.dp),
            ) { person ->
                SelectedItem(person) {

                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(state.persons){person->
                    PersonItem(
                        person=person
                    ){
                        onEvent(SelectPersonEvent.OnClickPerson(it))
                    }
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    onEvent(SelectPersonEvent.OnClickContinue)
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "continue",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectPersonScreenPrivew() {
    SelectPersonBody(
        state = SelectPersonState(
            selectedPersons = listOf(
                Person(
                    id = "456",
                    name = "sivi",
                    imageUrl = ""
                ),
                Person(
                    id = "456",
                    name = "sivi",
                    imageUrl = ""
                )
            ),
            persons = listOf(
                Person(
                    id = "456",
                    name = "sivi",
                    imageUrl = ""
                ),
                Person(
                    id = "456",
                    name = "sivi",
                    imageUrl = ""
                )
            )
        ),
        onEvent = {}
    )
}