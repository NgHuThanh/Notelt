package com.example.testnav

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar() {
    Box(modifier= Modifier
        .height(100.dp)
        .fillMaxWidth()
    ){
        TopAppBar(
            modifier=Modifier.padding(top=0.dp),

            title = {
                IconButton(onClick = {
                    // Handle the click action for the title here

                }) {
                    Text(text = "Clickable Title", color = Color.White)
                }
            },

            actions = {
                AppBarActions()
            }
        )
    }
}

@Composable
fun AppBarActions() {
    SearchAction()
    MoreAction()
}

@Composable
fun SearchAction() {
    val context=LocalContext.current
    IconButton(
        onClick={
            Toast.makeText(context,"Search clicked",Toast.LENGTH_LONG).show()
        }
    ) {
        Icon(
            imageVector=Icons.Filled.Search,
            contentDescription = "search_icon",
            tint =Color.White
        )
    }
}
@Composable
fun MoreAction() {
    val context=LocalContext.current
    IconButton(
        onClick={
            Toast.makeText(context,"More clicked",Toast.LENGTH_LONG).show()
        }
    ) {
        Icon(
            imageVector=Icons.Filled.MoreVert,
            contentDescription = "more_icon",
            tint =Color.White
        )
    }
}
