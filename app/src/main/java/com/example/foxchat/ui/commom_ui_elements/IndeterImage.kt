package com.example.foxchat.ui.commom_ui_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun InderterImage(
    defaultImage:Int,
    url:String?=null,
    sizeDifference:Int=2,
    modifier: Modifier= Modifier
){
    val localDensity = LocalDensity.current
    var heightDp by remember {
        mutableStateOf(0f.dp)
    }
    var widthDp by remember {
        mutableStateOf(0f.dp)
    }

    if (url==null){
        Box(
            contentAlignment = Alignment.Center,
            modifier=modifier.onGloballyPositioned {coordinates->
                heightDp= with(localDensity){coordinates.size.height.toDp()}
                widthDp= with(localDensity){coordinates.size.height.toDp()}
            }
        ) {
            Image(
                painter = painterResource(defaultImage),
                contentDescription = null,
                modifier = Modifier.size(width = widthDp/2, height = heightDp/2)
            )
        }
    }
    else{
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}