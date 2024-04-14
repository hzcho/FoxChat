package com.example.foxchat.ui.screens.sign_in_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import com.example.foxchat.utils.UiEvent
import com.example.foxchat.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
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

    SignInBody(
        state = viewModel.signInState.collectAsState().value,
        onEvent = viewModel::onEvent,
        snackBarHost = {
            SnackbarHost(snackBarHostState)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInBody(
    state: SignInState,
    onEvent: (SignInEvent) -> Unit,
    snackBarHost:@Composable ()->Unit
) {
    Scaffold(
        snackbarHost = { snackBarHost() }
    ) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (welcomeImage, emailTF, passwordTF, signUpButton, signInText) = createRefs()

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
            value = state.email,
            onValueChange = { newText ->
                onEvent(
                    SignInEvent.OnEmailTextChange(email = newText)
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
                top.linkTo(welcomeImage.bottom, margin = 32.dp)
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
                    SignInEvent.OnPasswordTextChange(password = newText)
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
                onEvent(SignInEvent.OnSignIn)
            },
            modifier = Modifier.constrainAs(signUpButton) {
                bottom.linkTo(parent.bottom, margin = 56.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(text = "Sign In")
        }

        Text(
            text = buildAnnotatedString {
                append("do you already have an account?")
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue
                    )
                ) {
                    append(" Sign Up")
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
                onEvent(SignInEvent.OpenSignUpScreen)
            }
        )
    }}
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInBody(
        state = SignInState(),
        onEvent = {},
        snackBarHost = {}
    )
}