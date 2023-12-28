package com.example.testnav

import androidx.compose.runtime.Composable
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.FavoriteBorder

import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.protobuf.NullValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import com.example.testnav.getImageList as getImageList1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavHostController, dataviewModel: VocabModel = viewModel()) {

    val getFolders = dataviewModel.state.value
    dataviewModel.getData("momy")

    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var vocabList by remember { mutableStateOf(emptyList<Vocab>()) }
    vocabList=getFolders


    Scaffold(
        //modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
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
                    IconButton(onClick = {expanded = true  },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Localized description"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        DropdownMenuItem(text = { Text("Review Settings") }, onClick = { expanded = false })
                        DropdownMenuItem(text = { Text("Display Archived") }, onClick = { expanded = false })
                        DropdownMenuItem(text = { Text("Sync") }, onClick = { expanded = false })
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ){
        ImageListScreen(imageList = getImageList1())
    }
}

@Composable
fun ImageListScreen(imageList: List<ImageItem>) {
    var currentIndex by remember { mutableStateOf(0) }
    val maxIndex = imageList.size - 1
    var unsplashPhoto by remember { mutableStateOf<UnsplashPhoto?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    //Đang test api

    performSearch(query = imageList[currentIndex].word) { photo, error ->
        if (photo != null) {
            unsplashPhoto = photo
        } else {
            isError = true
        }
        isLoading = false
    }
    isLoading = true



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .padding(top = 90.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            if (unsplashPhoto != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    // Coil Image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, Color.White) // Độ dày và màu sắc của viền
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = unsplashPhoto!!.urls.regular,
                                builder = {
                                    // Add any transformations here if needed
                                    transformations(RoundedCornersTransformation(16f))
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Text(
                        text = unsplashPhoto!!.user.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            } else if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            } else if (isError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                text = imageList[currentIndex].word,
                modifier = Modifier
                    .weight(1f), // Text chiếm toàn bộ phần còn lại của hàng
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    // Xử lý sự kiện khi nút được nhấn
                },
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)

            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder, // Icon mặc định (ở đây là Favorite)
                    contentDescription = "Favorite Icon"
                )
            }
        }

        Text(
            text = "Defination: ${imageList[currentIndex].defination}",
            modifier = Modifier
                .weight(1f), // Text chiếm toàn bộ phần còn lại của hàng
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    if (currentIndex > 0) {
                        currentIndex--
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous"
                )
            }
            //Text(text = "${imageList[currentIndex].id}/5")
            IconButton(
                onClick = {
                    if (currentIndex < maxIndex) {
                        currentIndex++

                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Next",
                    modifier = Modifier.rotate(180f) // Rotate icon for the next button
                )
            }
        }
    }
}
data class ImageItem(val id: Int, val word: String, val defination: String, val heart: Boolean,val link:String,val user:String)

@Composable
fun getImageList(): List<ImageItem> {

//    val vd:String
//    vd="https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=92f3e02f63678acc8416d044e189f515"
    val originalList = listOf(
        ImageItem(id = 1, word = "morning",defination="Sáng", link = "", heart = true,user=""),
        ImageItem(id = 2, word = "noon", defination="Trưa",link = "", heart = true,user=""),
        ImageItem(id = 3, word = "afternoon", defination="Chiều",link = "", heart = true,user=""),
        ImageItem(id = 4, word = "night", defination="Tối",link = "", heart = true,user=""),
        ImageItem(id = 5, word = "midnight", defination="Giữa đêm",link = "", heart = true,user=""),
        ImageItem(id = 6, word = "cold",defination="Lạnh", link = "", heart = true,user=""),
        ImageItem(id = 7, word = "hot", defination="Nóng",link = "", heart = true,user=""),
        ImageItem(id = 8, word = "ice", defination="Băng",link = "", heart = true,user=""),
        ImageItem(id = 9, word = "fire", defination="Lửa",link = "", heart = true,user=""),
        ImageItem(id = 10, word = "house", defination="Ngôi nhà",link = "", heart = true,user=""),
        // Add more images as needed
    )

    // Replace with your actual image resources and descriptions
    return originalList
}


@Composable
fun performSearch(query: String, onResult: (UnsplashPhoto?, Exception?) -> Unit) {
    val unsplashApiKey = "LXaGi4Y2hOPnddHNuzaEIv9sbsaa96XbUU_g-ziNZIc" // Thay thế bằng API key của bạn
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val unsplashService = remember(retrofit) {
        retrofit.create(UnsplashService::class.java)
    }

    LaunchedEffect(query) {
        try {
            val response = withContext(Dispatchers.IO) {
                unsplashService.searchPhotos(query, unsplashApiKey)
            }
            val photo = response.results.firstOrNull()
            onResult(photo, null)
        } catch (e: Exception) {
            onResult(null, e)
        }
    }
}