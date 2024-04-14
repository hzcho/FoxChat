package com.example.foxchat.ui.screens.chat_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.foxchat.data.model.Chat
import com.example.foxchat.ui.theme.FoxChatTheme
import com.example.foxchat.utils.UiEvent

@Composable
fun ChatScreen(
    navController:NavHostController,
    viewModel:ChatViewModel= hiltViewModel()
){
    LaunchedEffect(key1 = null){
        viewModel.uiEvent.collect{event->
            when(event){
                UiEvent.PopBackStack -> {
                    navController.popBackStack()
                }
                else ->{}
            }
        }
    }
    ChatBody(
        state = viewModel.chatState.collectAsState().value,
        onEvent = viewModel::onEvent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatBody(
    state:ChatState,
    onEvent:(ChatEvent)->Unit
){
    Scaffold(
        topBar = {
                 ChatTopBar(
                     chat = state.chat,
                     onClickBack = {
                         onEvent(ChatEvent.OnCLickBack)
                     }
                 )
        },
        bottomBar = {
            MessageBottomBar(
                messageText = state.messageText,
                onMessageChange = {newMessage->
                    onEvent(ChatEvent.OnMessageChange(newMessage))
                },
                onSendMessage = {
                    onEvent(ChatEvent.SendMessage)
                }
            )
        }
    ) {paddingValue->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 100.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValue.calculateTopPadding(),
                    bottom = paddingValue.calculateBottomPadding()
                )
        ) {
            items(state.messages){message->
                MessageItem(message)
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview(){
    FoxChatTheme {
        ChatBody(
            state = ChatState(
                messageText = "sisi",
                chat = Chat(name = "perdi")
            ),
            onEvent = {}
        )
    }
}