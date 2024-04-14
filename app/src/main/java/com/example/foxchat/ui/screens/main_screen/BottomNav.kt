package com.example.foxchat.ui.screens.main_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun BottomNav(currentRoute: String?, navigate: (String) -> Unit) {
    val itemList = listOf(
        BottomNavItem.ChatItem,
        BottomNavItem.PeopleItem,
        BottomNavItem.ProfileItem
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        itemList.forEach { item ->
            BottomNavigationItem(
                selected = item.route == currentRoute,
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                //unselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        painterResource(item.id),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text=item.title)
                },
                onClick = {
                    navigate(item.route)
                }
            )
        }
    }
}