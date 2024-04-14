package com.example.foxchat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.foxchat.data.reposiotry.PersonRepositoryImpl
import com.example.foxchat.data.reposiotry.UserRepositoryImpl
import com.example.foxchat.ui.navigation.MainNavGraph
import com.example.foxchat.ui.theme.FoxChatTheme
import com.example.foxchat.utils.FireRoutes
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoxChatTheme {
                val navController= rememberNavController()
                MainNavGraph(navController)
            }
        }
    }
}
