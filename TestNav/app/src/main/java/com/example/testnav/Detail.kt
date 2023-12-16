package com.example.testnav

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    names: List<String> = List(100) { "$it" }
)
{
    LazyVerticalGrid(
        GridCells.Fixed(2), // Set the number of items per row
        modifier = modifier.padding(vertical = 2.dp)
    ) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}
@Composable
private fun Greeting(name: String) {
    Surface(
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .background(Color.Gray, shape = RoundedCornerShape(10.dp))
                .border(
                    width = 0.dp,               // Độ dày của đường viền
                    color = Color.Black,         // Màu của đường viền
                    shape = RoundedCornerShape(10.dp)      // Hình dạng của đường viền (ở đây là hình chữ nhật)
                )
                .padding(10.dp)                  // Tăng khoảng cách giữa đường viền và nội dung
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Word: $name")
                Text(text = "Defination: $name")
                Row {
                    VolumeIconButton(onClick = {
                        // Handle click action here
                    })
                    HeartIconButton(onClick={

                    })
                    SettingIconButton(onClick={

                    })

                }
            }
        }
    }
}
@Composable
fun VolumeIconButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Volume",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
fun SettingIconButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Volume",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
fun HeartIconButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Like",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

//Column (modifier=Modifier.fillMaxSize(),
//verticalArrangement=Arrangement.Center,
//horizontalAlignment = Alignment.CenterHorizontally
//)
//{
//    Text(text="Detail screen")
//    Button(onClick = {
//
//    }) {
//        Text("Home")
//    }
//}