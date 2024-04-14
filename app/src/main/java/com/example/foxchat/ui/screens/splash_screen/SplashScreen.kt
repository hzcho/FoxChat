package com.example.foxchat.ui.screens.splash_screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foxchat.utils.UiEvent
import com.example.foxchat.R

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = null) {
        viewModel.onEvent(SplashEvent.StartAnimation)
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                else -> {}
            }
        }
    }

    SplashBody(
        state=viewModel.splashState.collectAsState().value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SplashBody(
    state: SplashState,
    onEvent: (SplashEvent) -> Unit
) {
    AnimatedSplash(state){
        onEvent(SplashEvent.OnFinishedAnimation)
    }
}

@Composable
private fun AnimatedSplash(
    state: SplashState,
    onFinished:()->Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (state.isAnimate) 1f else 0.4f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ),
        label = "",
        finishedListener = {
            onFinished()
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.fox),
            contentDescription = "splash image",
            modifier = Modifier.size((200*alpha).dp).alpha(alpha)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    SplashBody(
        state = SplashState(isAnimate = false),
        onEvent = {}
    )
}