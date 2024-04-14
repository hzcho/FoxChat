package com.example.foxchat.ui.screens.create_chat_screen

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BackTopBar(
    onClickBack: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        IconButton(onClick = {
            onClickBack()
        }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back"
            )
        }

        Text(
            text = "New chat",
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}