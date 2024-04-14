package com.example.foxchat.ui.screens.profile_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.foxchat.data.model.User
import com.example.foxchat.ui.commom_ui_elements.InderterImage
import com.example.foxchat.utils.UiEvent
import com.example.foxchat.R

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val photoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.onEvent(ProfileEvent.SetImage(uri))
    }
    LaunchedEffect(key1 = null) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                is UiEvent.PhotoPick -> {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }

                else -> {}
            }
        }
    }

    ProfileBody(
        state = viewModel.profileState.collectAsState().value,
        onEvent = viewModel::onEvent,
        dialog = {
            SettingsDialog(
                state = viewModel.dialogState.collectAsState().value,
                onEvent = viewModel::onDialogEvent
            )
        }
    )
}

@Composable
fun ProfileBody(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    dialog: @Composable () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (profileImage, profileLine, nameText, writeIcon, emailText, signOutButton) = createRefs()

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
                .constrainAs(profileLine) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, 64.dp)
                }
        )

        InderterImage(
            defaultImage = R.drawable.add_photo,
            url = state.user.imageUrl,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = CircleShape
                )
                .clickable {
                    onEvent(ProfileEvent.OnImageClick)
                }
                .constrainAs(profileImage) {
                    start.linkTo(parent.start, margin = 32.dp)
                    top.linkTo(profileLine.top)
                    bottom.linkTo(profileLine.bottom)
                }
        )

        Text(
            text = state.user.name,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            ),
            modifier = Modifier
                .clickable {
                    onEvent(ProfileEvent.OnNameClick)
                }
                .constrainAs(nameText) {
                start.linkTo(profileImage.end, margin = 12.dp)
                bottom.linkTo(profileLine.top, margin = 4.dp)
            }
        )

        Icon(
            painter = painterResource(R.drawable.icon_write),
            contentDescription = "change name",
            modifier = Modifier
                .size(22.dp)
                .constrainAs(writeIcon) {
                    start.linkTo(nameText.end, margin = 4.dp)
                    top.linkTo(nameText.top)
                    bottom.linkTo(nameText.bottom)
                }
        )

        Text(
            text = state.user.email,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.constrainAs(emailText) {
                start.linkTo(profileImage.end, margin = 12.dp)
                top.linkTo(profileLine.bottom, margin = 6.dp)
            }
        )

        Button(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.constrainAs(signOutButton) {
                bottom.linkTo(parent.bottom, margin = 64.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            },
            onClick = {
                onEvent(ProfileEvent.SignOut)
            }
        ) {
            Text(
                text = "sign out"
            )
        }
    }

    if (state.isShowDialog) {
        dialog()
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileBody(
        state = ProfileState(
            user = User(
                name = "sisi",
                email = "ebal@email.com"
            )
        ),
        onEvent = {},
        dialog = {}
    )
}