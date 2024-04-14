package com.example.foxchat.ui.screens.select_person_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foxchat.data.model.Person

@Composable
fun SelectedContainer(
    selectedPersons:List<Person>,
    modifier: Modifier = Modifier,
    item:@Composable (Person)->Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (selectedPersons.isEmpty()) {
            Text(text = "unselected")
        }

        LazyRow(
            modifier = Modifier.fillMaxSize().padding(start = 8.dp, end=8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(selectedPersons) { person ->
                item(person)
            }
        }
    }
}