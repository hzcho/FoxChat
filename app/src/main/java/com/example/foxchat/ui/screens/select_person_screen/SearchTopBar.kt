package com.example.foxchat.ui.screens.select_person_screen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    query:String,
    onClickBack:()->Unit,
    onQueryChange:(String)->Unit,
    onSearch:(String)->Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.height(68.dp)
    ) {

        IconButton(
            onClick = {
                onClickBack()
            }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back"
            )
        }

        SearchBar(
            query = query,
            active = false,
            onQueryChange = { newQuery ->
                onQueryChange(newQuery)
            },
            onActiveChange = {

            },
            onSearch = {query->
                onSearch(query)
            },
            shape = RoundedCornerShape(8.dp),
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
            colors = SearchBarDefaults.colors(
                //containerColor = MaterialTheme.colorScheme.primary
                inputFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ),
            modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
        ) {

        }
    }
}