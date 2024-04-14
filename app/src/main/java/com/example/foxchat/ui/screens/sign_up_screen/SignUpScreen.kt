package com.example.foxchat.ui.screens.sign_up_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foxchat.R
import com.example.foxchat.utils.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = null) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }

                else -> {}
            }
        }
    }
    SignUpBody(
        state = viewModel.signUpEvent.collectAsState().value,
        onEvent = viewModel::onEvent,
        snackBarHost = {
            SnackbarHost(snackBarHostState)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpBody(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    snackBarHost:@Composable () -> Unit
) {
    Scaffold(
        snackbarHost = { snackBarHost() }
    ) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (welcomeImage, nameTF, emailTF, passwordTF, signUpButton, signInText) = createRefs()

        Image(
            painterResource(R.drawable.welcome),
            contentDescription = "welcome",
            modifier = Modifier.constrainAs(welcomeImage) {
                top.linkTo(parent.top, margin = 48.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        TextField(
            shape = RoundedCornerShape(8.dp),
            value = state.name,
            onValueChange = { newText ->
                onEvent(
                    SignUpEvent.OnNameTextChange(name = newText)
                )
            },
            placeholder = {
                Text(text = "name")
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "person"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.constrainAs(nameTF) {
                top.linkTo(welcomeImage.bottom, margin = 32.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            }
        )

        TextField(
            shape = RoundedCornerShape(8.dp),
            value = state.email,
            onValueChange = { newText ->
                onEvent(
                    SignUpEvent.OnEmailTextChange(email = newText)
                )
            },
            placeholder = {
                Text(text = "email")
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "email"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.constrainAs(emailTF) {
                top.linkTo(nameTF.bottom, margin = 20.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            }
        )

        TextField(
            shape = RoundedCornerShape(8.dp),
            value = state.password,
            onValueChange = { newText ->
                onEvent(
                    SignUpEvent.OnPasswordTextChange(password = newText)
                )
            },
            placeholder = {
                Text(text = "password")
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "password"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.constrainAs(passwordTF) {
                top.linkTo(emailTF.bottom, margin = 20.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            }
        )

        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = {
                onEvent(SignUpEvent.OnSignUp)
            },
            modifier = Modifier.constrainAs(signUpButton) {
                bottom.linkTo(parent.bottom, margin = 56.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(text = "Sign Up")
        }

        Text(
            text = buildAnnotatedString {
                append("do you already have an account?")
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue
                    )
                ) {
                    append(" Sign In")
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(signInText) {
                top.linkTo(signUpButton.bottom, margin = 16.dp)
                start.linkTo(signUpButton.start)
                end.linkTo(signUpButton.end)
                width = Dimension.fillToConstraints
            }.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onEvent(SignUpEvent.OpenSignInScreen)
            }
        )
    }}
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpBody(
        state = SignUpState(),
        onEvent = {},
        snackBarHost = {}
    )
}