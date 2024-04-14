package com.example.foxchat.ui.screens.chat_screen

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.foxchat.data.model.Message
import org.intellij.lang.annotations.PrintFormat

@Composable
fun MessageItem(
    message: MessageState
) {
    Card {
        ConstraintLayout(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)) {
            val (messageText, timeText) = createRefs()

            Text(
                text = if(message.text.isEmpty()) "sisi" else message.text,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.constrainAs(messageText) {

                }
            )

            Text(
                text = if(message.time.isEmpty()) "sisi" else message.time,
                style = TextStyle(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.constrainAs(timeText) {
                    top.linkTo(messageText.bottom, margin = -4.dp)
                    start.linkTo(messageText.end, margin = 8.dp)
                }
            )
        }
    }
}

@Preview
@Composable
fun MessageItemPreview(){
    MessageItem(
        message = MessageState(
            text = "sisi"
        )
    )
}