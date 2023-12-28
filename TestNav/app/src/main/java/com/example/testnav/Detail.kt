package com.example.testnav

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.testnav.model.getVocabsForFolderWithTitle
import kotlinx.coroutines.runBlocking

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    folder: String,
    modifier: Modifier = Modifier,
    names: List<String> = List(100) { "$it" },
    dataviewModel: VocabModel = viewModel(),

)
{
    val getFolders = dataviewModel.state.value
    dataviewModel.getData(folder)

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
                items(items = getFolders) { name ->
                    Greeting(name = name,parent=folder)
                }
            }
        }

    }

}
@Composable
fun rememberTextToSpeechManager(context: Context): TextToSpeechManager {
    val textToSpeechManager = remember(context) {
        TextToSpeechManager(context)
    }

    DisposableEffect(textToSpeechManager) {
        onDispose {
            textToSpeechManager.shutdown()
        }
    }

    return textToSpeechManager
}
@Composable
fun Greeting(name: Vocab,parent:String,


) {
    val textToSpeechManager = rememberTextToSpeechManager(LocalContext.current)
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
                        text = "${name.word}",
                        style = MaterialTheme.typography.displaySmall // Hoặc bất kỳ kiểu chữ nào có kích thước lớn hơn
                    )
                    Text(
                        text = "${name.definition}",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Row {
                        VolumeIconButton(textToSpeechManager, name.word)
                        ToggleIconButton(name.heart,name.word,parent=parent)
                        SettingIconButton(onClick={
                        })

                    }
                }
            }
        }

    }
}
@Composable
fun VolumeIconButton(textToSpeechManager: TextToSpeechManager, textToSpeak: String) {
    IconButton(
        onClick = {
            textToSpeechManager.speak(textToSpeak)
        },
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
fun ToggleIconButton(liked:Boolean,word:String,parent: String) {
    var isFavorite by remember { androidx.compose.runtime.mutableStateOf(liked)}
    IconButton(
        onClick = {
            isFavorite = !isFavorite
            runBlocking { if(liked)
                updateHeartStatusBasedOnWord(parent,word,false)
            else
                updateHeartStatusBasedOnWord(parent,word,true)
            }
        },
        modifier = Modifier.padding(16.dp)
    ) {
        // Sử dụng biến trạng thái để chọn icon phù hợp
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite Icon"
        )
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
