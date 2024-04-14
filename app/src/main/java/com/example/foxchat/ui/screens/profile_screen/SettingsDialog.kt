package com.example.foxchat.ui.screens.profile_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SettingsDialog(
    state: DialogState,
    onEvent: (DialogEvent) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onEvent(DialogEvent.OnDismiss)
        }
    ) {
        Card {
            ConstraintLayout(modifier = Modifier.padding(8.dp)) {
                val (nameTF, buttonBar) = createRefs()
                TextField(
                    placeholder = {
                        Text(text = "name")
                    },
                    value = state.name,
                    onValueChange = { newName ->
                        onEvent(DialogEvent.OnNameChange(newName))
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().constrainAs(nameTF) {

                    }
                )

                Row(
                    modifier = Modifier.constrainAs(buttonBar) {
                        start.linkTo(nameTF.start)
                        end.linkTo(nameTF.end)
                        top.linkTo(nameTF.bottom, margin = 16.dp)
                    }
                ) {
                    Button(onClick = {
                        onEvent(DialogEvent.OnDismiss)
                    }) {
                        Text("cansel")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { onEvent(DialogEvent.OnConfirm) }) {
                        Text("ok")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsDialogPriview() {
    SettingsDialog(
        state = DialogState(),
        onEvent = {}
    )
}