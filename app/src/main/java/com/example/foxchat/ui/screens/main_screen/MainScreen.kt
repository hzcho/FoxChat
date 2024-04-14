package com.example.foxchat.ui.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foxchat.utils.UiEvent
import com.example.foxchat.ui.navigation.BottomNavGraph

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val subNavController= rememberNavController()

    LaunchedEffect(key1 = null) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is UiEvent.SubNavigate->{
                    subNavController.navigate(event.route)
                }
                else -> {}
            }
        }
    }
    MainBody(
        state = viewModel.mainState.collectAsState().value,
        onEvent = viewModel::onEvent
    ){
        BottomNavGraph(
            navController=navController,
            subNavController=subNavController
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainBody(
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    content:@Composable ()->Unit
) {
    Scaffold(
        bottomBar = {
              BottomNav(state.currentRoute){route->
                  onEvent(MainEvent.Navigate(route))
              }
        },
        floatingActionButton = {
            if (state.isShowFAB) {
                FloatingActionButton(
                    onClick = { onEvent(MainEvent.OnClickFAB) }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "FAB"
                    )
                }
            }
        }
    ) {
        content()
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainBody(
        state = MainState(),
        onEvent = {},
        content = {}
    )
}