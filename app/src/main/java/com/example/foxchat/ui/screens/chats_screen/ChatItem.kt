package com.example.foxchat.ui.screens.chats_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foxchat.R
import com.example.foxchat.data.model.Chat
import com.example.foxchat.ui.commom_ui_elements.InderterImage

@Composable
fun ChatItem(
    chat: Chat,
    onClick:(Chat)->Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(chat)
            }
    ) {

        InderterImage(
            defaultImage = R.drawable.default_chat_image,
            url = chat.imageUrl,
            modifier = Modifier.size(64.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
        )

        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
            Text(
                text = chat.name,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = chat.lastMessage?.text ?: "no message",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Light
                )
            )
        }
        IconButton(onClick = {}, modifier = Modifier.padding(start = 8.dp)) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "cansel"
            )
        }
    }
}