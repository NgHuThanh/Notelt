//@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.testnav


import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material.icons.twotone.Create
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.testnav.model.DataviewModel
import kotlinx.coroutines.runBlocking




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController:NavHostController,
   modifier: Modifier = Modifier,
   dataviewModel: DataviewModel = viewModel(),
) {
    val getFolders = dataviewModel.state.value

    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val isShowDialogAdd =remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialogAdd.value=true },
                modifier = Modifier
                    .padding(25.dp)
                    .size(75.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },

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
        if(isShowDialogAdd.value){
            alertDialogAdd(dataviewModel)
        }
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
                    onClick = {
                        //navController.navigate("Detail")
                              },

                ) {
                    Text(
                        "Review All",

                    )
                }
            }

            LazyColumn(modifier = modifier
                .padding(horizontal=2.dp))
            {
                items(getFolders) { folder ->
                    DetailTopic(
                        folder.title,
                        navController,
                        dataviewModel
                    )
                }
            }


        }
    }
    }



@Composable
private fun DetailTopic(folder: String?, navController: NavHostController,dataviewModel: DataviewModel) {
    var expandedTopic by remember { mutableStateOf(false) }
    val isShowDialog = remember { mutableStateOf(false) }
    val isShowDialogUpdate =remember { mutableStateOf(false) }
    var delword by remember { mutableStateOf("") }
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
                    Text(text = "${folder}")
                    Text(text = "1/20",
                        style = TextStyle(
                            fontSize = 12.sp, // Đặt kích thước chữ là 12sp (có thể điều chỉnh theo ý muốn)
                            color = LocalContentColor.current.copy(alpha = 0.8f) // Màu xám với độ trong suốt 0.8
                        )
                    )
                    Row {
                        Button(
                            onClick = {isShowDialog.value=true},
                        ) {
                            Text(
                                "Practice",
                            )
                        }
                        if(isShowDialog.value){
                            alertDialog(navController)
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
                                //Text("${folder}")
                                Text("Edit")
                            }
                                                }, onClick = {
                            isShowDialogUpdate.value=true
                                                })
                        if(isShowDialogUpdate.value){
                            alertDialogUpdate("${folder}",dataviewModel)
                        }
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
                        }, onClick = {
                            delword="${folder}"
                            expandedTopic = false
                            runBlocking{
                                dataviewModel.deleteFoldersWithTitleNone("${folder}")
                            }
                        })


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun alertDialogAdd(dataviewModel: DataviewModel) {
    val context= LocalContext.current
    val openDialog=remember{ mutableStateOf(true) }
    var text by remember { mutableStateOf("") }

    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value=false },
            title={
                Text(text = "Add new section")
            },
            text={
                 Column {
                     TextField(
                         value = text,
                         onValueChange = { text = it },
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(16.dp),
                         keyboardOptions = KeyboardOptions(
                             keyboardType = KeyboardType.Text,
                             imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                             capitalization = KeyboardCapitalization.Sentences
                         ),
                         textStyle = LocalTextStyle.current.copy(
                             fontSize = 16.sp // Kích thước chữ
                         ),
                         singleLine = true,
                         placeholder = { Text("Name") }
                     )
                 }
            },
            confirmButton = {
                Button(onClick = {
                    openDialog.value = false
                    Toast.makeText(context,"Confirm",Toast.LENGTH_SHORT).show()
                    //addItemToFirebase(text)
                    runBlocking {
                        dataviewModel.createFolder(text)
                    }

                }) {
                    Text(text="Add section")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                    Toast.makeText(context,"Dismiss",Toast.LENGTH_SHORT).show()
                }) {
                    Text(text="Cancle")
                }
            },
            )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun alertDialogUpdate(title:String,dataviewModel: DataviewModel) {
    val context= LocalContext.current
    val openDialogUp=remember{ mutableStateOf(true) }
    var text by remember { mutableStateOf("") }
    var mainText by remember { mutableStateOf("") }

    var isUpdateButtonClicked by remember { mutableStateOf(false) }

    if (openDialogUp.value){
        AlertDialog(
            onDismissRequest = { openDialogUp.value=false },
            title={
                Text(text = "Update title ${title}")
            },
            text={
                Column {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 16.sp // Kích thước chữ
                        ),
                        singleLine = true,
                        placeholder = { Text("Name") }
                    )
                }
            },

            confirmButton = {
                Button(onClick = {
                    openDialogUp.value = false
                    Toast.makeText(context,"Confirm",Toast.LENGTH_SHORT).show()
                    isUpdateButtonClicked = true
                    mainText=text
                    runBlocking {
                        dataviewModel.updateFoldersWithTitleNone(mainText, title)
                    }
                }) {
                    Text(text="Update")
                }

            },
            dismissButton = {
                Button(onClick = {
                    openDialogUp.value = false
                    Toast.makeText(context,"Dismiss",Toast.LENGTH_SHORT).show()
                }) {
                    Text(text="Cancle")
                }
            },

            )
    }
}
@Composable
fun alertDialog(navController:NavHostController) {
    val PracticeMenus= listOf(
        PracticeMenu(Icons.TwoTone.Check,"Basic Review","Basic Flashcards review","Review"),
        PracticeMenu(Icons.TwoTone.Search,"Multiple answers","Select the correct answer","None"),
        PracticeMenu(Icons.TwoTone.Create,"Write Review","Review by writing word","None"),
        PracticeMenu(Icons.TwoTone.LocationOn,"Match Cards","Math between two word","None"),
    )
    val context= LocalContext.current
    val openDialog=remember{ mutableStateOf(true) }
    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value=false },
            title={
                  Text(text = "Practive option")
            },
            text={
                Column {
                    PracticeMenus.forEach{
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(width = 500.dp, height = 100.dp)
                                .padding(18.dp),
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate(it.screen)
                                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(width = 500.dp, height = 100.dp),
                                ) {
                                Icon(imageVector=it.icon,contentDescription = "")
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = it.title, style = MaterialTheme.typography.labelLarge)
                                    Text(
                                        text = it.content,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
                                    )
                                }
                            }

                        }
                    }

                }
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    Toast.makeText(context,"Confirm",Toast.LENGTH_SHORT).show()
                }) {
                Text(text="")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    Toast.makeText(context,"Dismiss",Toast.LENGTH_SHORT).show()
                }) {
                    Text(text="Trở lại")
                }
            },

            )
    }
}
data class PracticeMenu(
    val icon : ImageVector,
    val title : String,
    val content: String,
    val screen:String,
)