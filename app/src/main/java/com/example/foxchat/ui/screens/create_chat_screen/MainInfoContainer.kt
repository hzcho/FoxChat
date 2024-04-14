package com.example.foxchat.ui.screens.create_chat_screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foxchat.R

@Composable
fun MainInfoContainer(
    name: String,
    imageUrl: Uri?,
    onNameChange: (String) -> Unit,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            if (imageUrl==null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable {
                            onImageClick()
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.add_photo),
                        contentDescription = "add chat photo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(40.dp)
                    )
                }
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "chat image",
                    modifier = Modifier.size(80.dp).clip(CircleShape)
                )
            }
            OutlinedTextField(
                value = name,
                onValueChange = { newValue ->
                    onNameChange(newValue)
                },
                modifier = Modifier.weight(1f).padding(start=8.dp)
            )
        }
    }
}