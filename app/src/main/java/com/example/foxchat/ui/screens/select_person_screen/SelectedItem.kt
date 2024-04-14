package com.example.foxchat.ui.screens.select_person_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.foxchat.R
import com.example.foxchat.data.model.Person

@Composable
fun SelectedItem(person: Person, onClick: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .clickable {
                person.id?.let { onClick(it) }
            }
    ) {
        val (profileImage, removeIcon) = createRefs()

        if (person.imageUrl == null) {
            Image(
                painter = painterResource(R.drawable.default_person),
                contentDescription = "default profile image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape).border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .constrainAs(profileImage) {

                    }
            )
        } else {
            AsyncImage(
                model = person.imageUrl,
                contentDescription = "profile image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape).border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .constrainAs(profileImage) {

                    }
            )
        }

        Icon(
            painter = painterResource(R.drawable.icon_cancel),
            contentDescription = "cansel",
            modifier = Modifier.constrainAs(removeIcon) {
                top.linkTo(profileImage.top, margin = -3.dp)
                end.linkTo(profileImage.end, margin = -3.dp)
            }
        )
    }
}