package com.example.testnav

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    names: List<String> = List(100) { "$it" },
)
{
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        //modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("Home") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {},
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Column(modifier=Modifier.padding(top = 70.dp)){
            Row {
                Button(
                    onClick = {},

                    ) {
                    Text(
                        "All",
                    )
                }
                Button(
                    onClick = {},

                    ) {
                    Text(
                        "Favorite only",
                        )
                }
            }
            LazyVerticalGrid(
                GridCells.Fixed(2), // Set the number of items per row
                modifier = modifier.padding(vertical = 2.dp)
            ) {
                items(items = names) { name ->
                    Greeting(name = name)
                }
            }
        }

    }

}
@Composable
private fun Greeting(name: String) {
    Surface(
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(5.dp)

        ){
            Row(
                modifier = Modifier
                    .padding(18.dp)
                 // Tăng khoảng cách giữa đường viền và nội dung
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "$name",
                        style = MaterialTheme.typography.displaySmall // Hoặc bất kỳ kiểu chữ nào có kích thước lớn hơn
                    )
                    Text(
                        text = "$name",
                        style = MaterialTheme.typography.labelLarge
                    )
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
            imageVector = Icons.Default.PlayArrow,
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