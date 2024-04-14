package com.example.foxchat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foxchat.ui.screens.chats_screen.ChatsScreen
import com.example.foxchat.ui.screens.profile_screen.ProfileScreen
import com.example.foxchat.ui.screens.search_screen.SearchScreen
import com.example.foxchat.utils.ScreenRoutes

@Composable
fun BottomNavGraph(
    navController:NavHostController,
    subNavController: NavHostController
) {
    NavHost(
        navController = subNavController,
        startDestination = ScreenRoutes.CHATS_SCREEN
    ) {
        composable(
            route=ScreenRoutes.CHATS_SCREEN
        ){
            ChatsScreen(navController)
        }
        composable(
            route=ScreenRoutes.PEOPLE_SCREEN
        ){
            SearchScreen()
        }
        composable(
            route=ScreenRoutes.PROFILE_SCREEN
        ){
            ProfileScreen(navController)
        }
    }
}