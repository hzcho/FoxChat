package com.example.foxchat.ui.screens.create_chat_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.navigation.NavController
import com.example.foxchat.data.model.Person
import com.example.foxchat.ui.commom_ui_elements.PersonItem
import com.example.foxchat.ui.screens.profile_screen.ProfileEvent
import com.example.foxchat.ui.screens.select_person_screen.SelectPersonEvent
import com.example.foxchat.utils.UiEvent

@Composable
fun CreateChatScreen(
    navController: NavController,
    viewModel: CreateChatViewModel = hiltViewModel()
) {
    val photoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.onEvent(CreateChatEvent.SetImage(uri))
    }
    LaunchedEffect(key1 = null) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    navController.popBackStack()
                }
                is UiEvent.PhotoPick -> {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                else -> {}
            }
        }
    }

    CreateChatBody(
        state = viewModel.createChatState.collectAsState().value,
        onEvent = viewModel::onEvent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateChatBody(
    state: CreateChatState,
    onEvent: (CreateChatEvent) -> Unit
) {
    Scaffold(
        topBar = {
            BackTopBar {
                onEvent(CreateChatEvent.OnClickBack)
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            MainInfoContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(
                        top = paddingValues.calculateTopPadding()
                    ),
                name = state.chatName,
                imageUrl = state.imageUri,
                onNameChange = { newName ->
                    onEvent(CreateChatEvent.OnNameChange(newName))
                },
                onImageClick = {
                    onEvent(CreateChatEvent.OnImageClick)
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(state.selectedPersons){person->
                    PersonItem(
                        person=person
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    onEvent(CreateChatEvent.OnChatCreate)
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "create",
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
fun CreateChatScreenPreview() {
    CreateChatBody(
        state = CreateChatState(
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
            )
        ),
        onEvent = {}
    )
}