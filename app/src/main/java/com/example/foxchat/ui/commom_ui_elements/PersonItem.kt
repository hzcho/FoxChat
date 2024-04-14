package com.example.foxchat.ui.commom_ui_elements

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import com.example.foxchat.R
import com.example.foxchat.data.model.Person

@Composable
fun PersonItem(
    person: Person,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onClick:(Person)->Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(person)
            }
    ) {
        InderterImage(
            defaultImage = R.drawable.default_person,
            url = person.imageUrl,
            modifier = Modifier.size(64.dp)
                .clip(CircleShape).border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
        )

        Text(
            text = person.name ?: "unknown",
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            ),
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            leadingIcon()
            trailingIcon()
        }
    }
}