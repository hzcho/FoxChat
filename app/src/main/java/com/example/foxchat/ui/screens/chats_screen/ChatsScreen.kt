package com.example.foxchat.ui.screens.chats_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foxchat.utils.UiEvent

@Composable
fun ChatsScreen(
    navController:NavHostController,
    viewModel: ChatsViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = null){
        viewModel.uiEvent.collect{event->
            when(event){
                is UiEvent.Navigate->{
                    navController.navigate(event.route)
                }
                else->{}
            }
        }
    }
    ChatsBody(
        state = viewModel.chatsState.collectAsState().value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ChatsBody(
    state: ChatsState,
    onEvent:(ChatsEvent)->Unit
){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.chats){ chat->
            ChatItem(chat){current_chat->
                onEvent(ChatsEvent.OnChatClick(current_chat))
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start=8.dp, end = 8.dp)
                .background(Color.Gray)
            )
        }
    }
}

@Preview
@Composable
fun ChatsScreenPreview(){
    ChatsBody(
        state = ChatsState(
            chats = listOf(

            )
        ),
        onEvent = {}
    )
}