package com.example.foxchat.ui.screens.chat_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foxchat.R
import com.example.foxchat.data.model.Chat

@Composable
fun ChatTopBar(
    chat: Chat,
    onClickBack: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    onClickBack()
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }

            if (chat.imageUrl == null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.default_chat_image),
                        contentDescription = "default profile image",
                        modifier = Modifier.size(32.dp)
                    )
                }
            } else {
                AsyncImage(
                    model = chat.imageUrl,
                    contentDescription = "profile image",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(48.dp)
                        .clip(CircleShape).border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                )
            }
            Text(
                text = chat.name,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun ChatTopBarPreview(){
    ChatTopBar(
        chat = Chat(
            name = "sisi",
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/fogiusab.appspot.com/o/images?alt=media&token=da7ad396-3812-463f-9dba-5e98586e9b8a"
        ),
        onClickBack = {}
    )
}