package com.example.foxchat.ui.screens.main_screen

import com.example.foxchat.R
import com.example.foxchat.utils.ScreenRoutes

sealed class BottomNavItem(val title:String, val id:Int, val route:String) {
    data object ChatItem: BottomNavItem(title = "chats", id= R.drawable.icon_chat, route = ScreenRoutes.CHATS_SCREEN)
    data object PeopleItem: BottomNavItem(title = "people", id = R.drawable.icon_people, route = ScreenRoutes.PEOPLE_SCREEN)
    data object ProfileItem: BottomNavItem(title = "profile", id = R.drawable.icon_profile, route = ScreenRoutes.PROFILE_SCREEN)
}