package com.example.foxchat.ui.screens.chat_screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MessageBottomBar(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            value = messageText,
            onValueChange = { newMessage ->
                onMessageChange(newMessage)
            },
            placeholder = {
                Text(text = "message")
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onSendMessage) {
            Icon(
                Icons.AutoMirrored.Filled.Send,
                contentDescription = "send button"
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    MessageBottomBar(
        messageText = "",
        onSendMessage = {},
        onMessageChange = {}
    )
}