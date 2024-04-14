package com.example.foxchat.ui.screens.search_screen

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier=Modifier
) {
    SearchBar(
        query = query,
        active = false,
        onQueryChange = { newQuery ->
            onQueryChange(newQuery)
        },
        onActiveChange = {

        },
        onSearch = { query ->
            onSearch(query)
        },
        leadingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "search"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "delete"
                )
            }
        },
        modifier = modifier
    ) {

    }
}