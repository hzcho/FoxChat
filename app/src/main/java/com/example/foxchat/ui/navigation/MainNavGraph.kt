package com.example.foxchat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foxchat.ui.screens.chat_screen.ChatScreen
import com.example.foxchat.ui.screens.create_chat_screen.CreateChatScreen
import com.example.foxchat.ui.screens.main_screen.MainScreen
import com.example.foxchat.ui.screens.select_person_screen.SelectPersonScreen
import com.example.foxchat.ui.screens.sign_in_screen.SignInScreen
import com.example.foxchat.ui.screens.sign_up_screen.SignUpScreen
import com.example.foxchat.ui.screens.splash_screen.SplashScreen
import com.example.foxchat.utils.ScreenRoutes

@Composable
fun MainNavGraph(navController:NavHostController){
    NavHost(navController=navController, startDestination = ScreenRoutes.SPLASH_SCREEN)  {
        composable(
            route = ScreenRoutes.SPLASH_SCREEN
        ){
            SplashScreen(navController)
        }
        composable(
            route = ScreenRoutes.SIGN_UP_SCREEN
        ){
            SignUpScreen(navController)
        }
        composable(
            route=ScreenRoutes.SIGN_IN_SCREEN
        ){
            SignInScreen(navController)
        }
        composable(
            route=ScreenRoutes.MAIN_SCREEN
        ){
            MainScreen(navController)
        }
        composable(
            route=ScreenRoutes.SELECT_PERSON_SCREEN
        ){
            SelectPersonScreen(navController)
        }
        composable(
            route=ScreenRoutes.CREATE_CHAT_SCREEN,
            arguments = listOf(
                navArgument(ScreenRoutes.PERSONS_ARG){
                    type=PersonNavType()
                }
            )
        ){
            CreateChatScreen(navController)
        }
        composable(
            route=ScreenRoutes.CHAT_SCREEN,
            arguments = listOf(
                navArgument(ScreenRoutes.CHAT_ARG){
                    type=ChatNavType()
                }
            )
        ){
            ChatScreen(navController)
        }
    }
}