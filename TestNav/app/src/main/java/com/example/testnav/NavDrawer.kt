@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.testnav
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline

import androidx.compose.material3.Divider

import androidx.compose.material3.DrawerValue

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem

import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                    Image(painter = painterResource(id=R.drawable.jj), contentDescription = "pic",
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
