@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.testnav
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.protobuf.NullValue

import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController:NavHostController,
               modifier: Modifier = Modifier,
               names: List<String> = List(1000) { "$it" }
) {
    var expanded by remember { mutableStateOf(false) }
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
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
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
                            imageVector = Icons.Filled.MoreVert,
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
    ) {innerPadding ->
        Column(modifier=Modifier.padding(top = 70.dp)){
            Row {
                Button(
                    onClick = {},

                ) {
                    Text(
                        "Practice All",
                    )
                }
                Button(
                    onClick = { navController.navigate("Detail")},

                ) {
                    Text(
                        "Review All",

                    )
                }
            }
            LazyColumn(modifier = modifier
                .padding(horizontal=2.dp))
            {
                items(items = names) { name ->
                    DetailTopic(
                        name = name,
                        navController
                    )
                }
            }
        }
    }
    }


//Scaffold(topBar = {MainAppBar()}) {

//}
@Composable
private fun DetailTopic(name: String, navController: NavHostController,) {
    var expandedTopic by remember { mutableStateOf(false) }
    Surface(
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 500.dp, height = 150.dp)
                .padding(18.dp)

        ) {
            Row{
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)) {
                    Text(text = name)
                    Text(text = "1/20",
                        style = TextStyle(
                            fontSize = 12.sp, // Đặt kích thước chữ là 12sp (có thể điều chỉnh theo ý muốn)
                            color = LocalContentColor.current.copy(alpha = 0.8f) // Màu xám với độ trong suốt 0.8
                        )
                    )
                    Row {
                        Button(
                            onClick = {},
                        ) {
                            Text(
                                "Practice",
                            )
                        }
                        Button(
                            onClick = { navController.navigate("Detail")},
                        ) {
                            Text(
                                "Review",
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                ) {
                    IconButton(onClick = {expandedTopic = true },
                        modifier = Modifier
                            .padding(16.dp)) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description"
                        )
                    }
                    DropdownMenu(
                        expanded = expandedTopic,
                        onDismissRequest = { expandedTopic = false },

                        ) {
                        DropdownMenuItem(text = {
                            Row{
                                Icon(
                                    imageVector = Icons.Filled.Create,
                                    contentDescription = "Localized description"
                                )
                                Text("Edit")
                            }
                                                }, onClick = { expandedTopic = false })
                        DropdownMenuItem(text = {
                            Row{
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Localized description"
                                )
                                Text("Add")
                            }
                        }, onClick = { expandedTopic = false })
                        DropdownMenuItem(text = {
                            Row{
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Localized description"
                                )
                                Text("Share")
                            }
                        }, onClick = { expandedTopic = false })
                        DropdownMenuItem(text = {
                            Row{
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Localized description"
                                )
                                Text("Archive")
                            }
                        }, onClick = { expandedTopic = false })
                        DropdownMenuItem(text = {
                            Row{
                                Icon(
                                    imageVector = Icons.Filled.ExitToApp,
                                    contentDescription = "Localized description"
                                )
                                Text("Export")
                            }
                        }, onClick = { expandedTopic = false })
                        DropdownMenuItem(text = {
                            Row{
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Localized description"
                                )
                                Text("Delete")
                            }
                        }, onClick = { expandedTopic = false })
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Localized description"
                        )
                    }
                }
            }

        }

    }
}
@Composable
fun NavDrawer()
{
    val drawerItems= listOf(
        DrawerItems(Icons.Default.List,"All Card","None"),
        DrawerItems(Icons.Default.DateRange,"Progress","None"),
        DrawerItems(Icons.Default.AddCircle,"Import","None"),
        DrawerItems(Icons.Default.MailOutline,"Contact","None"),
    )
    var selectedItem by remember {
        mutableStateOf(drawerItems[0])
    }
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Box(modifier= Modifier
                .fillMaxWidth()
                .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment =Alignment.CenterHorizontally,
                ) {
                    Image(painter = painterResource(id=R.drawable.ic_launcher_foreground), contentDescription = "pic",
                        modifier= Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                        )
                    Text(
                        text="Name người dùng",
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        fontSize=22.sp,
                        textAlign= TextAlign.Center
                    )
                }
                Divider(
                    Modifier.align(Alignment.BottomCenter),thickness=1.dp,
                    Color.DarkGray
                )
            }
            drawerItems.forEach{
                NavigationDrawerItem(label = { Text(text=it.text) }
                    , selected = it==selectedItem
                    , onClick = {
                                selectedItem=it
                        scope.launch{
                            drawerState.close()
                        }

                    },
                    modifier=Modifier.padding(horizontal=20.dp)
                    , icon={
                        Icon(imageVector=it.icon,contentDescription = it.text)
                    }

                )
            }
        }
    },drawerState=drawerState) {
        Nav()
    }
}
data class DrawerItems(
    val icon : ImageVector,
    val text : String,
    val screen: String,
)
//If want badget :https://www.youtube.com/watch?v=ZwVZz6eap94
