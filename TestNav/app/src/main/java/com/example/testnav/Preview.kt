package com.example.testnav

import androidx.compose.runtime.Composable
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material.icons.twotone.Create
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox

import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider

import androidx.compose.material3.DrawerValue

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.protobuf.NullValue

import androidx.navigation.NavHostController
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val isShowDialogAdd =remember { mutableStateOf(false) }
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
        ImageListScreen(imageList = getImageList())
    }
}
data class ImageItem(val id: Int, val painter: Painter, val description: String, val heart: Boolean)
@Composable
fun ImageListScreen(imageList: List<ImageItem>) {
    var currentIndex by remember { mutableStateOf(0) }
    val maxIndex = imageList.size - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Image(
            painter = imageList[currentIndex].painter,
            contentDescription = imageList[currentIndex].description,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(10.dp)
                .clip(shape = MaterialTheme.shapes.medium),

            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                text = imageList[currentIndex].description,
                modifier = Modifier
                    .weight(1f), // Text chiếm toàn bộ phần còn lại của hàng
                style = MaterialTheme.typography.displayMedium,
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
            text = "defination: ${imageList[currentIndex].description}",
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
            Text(text = "${imageList[currentIndex].id}/5")
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
@Composable
fun getImageList(): List<ImageItem> {
    // Replace with your actual image resources and descriptions
    return listOf(
        ImageItem(
            id = 1,
            painter = painterResource(id = R.drawable.puppy),
            description = "Image 1",
            heart=true,
        ),
        ImageItem(
            id = 2,
            painter = painterResource(id = R.drawable.puppy2),
            description = "Image 2",
            heart=true,
        ),
        ImageItem(
            id = 3,
            painter = painterResource(id = R.drawable.puppy3),
            description = "Image 2",
            heart=true,
        ),
        ImageItem(
            id = 4,
            painter = painterResource(id = R.drawable.puppy),
            description = "Image 1",
            heart=true,
        ),
        ImageItem(
            id = 5,
            painter = painterResource(id = R.drawable.puppy2),
            description = "Image 2",
            heart=true,
        ),
        // Add more images as needed
    )
}