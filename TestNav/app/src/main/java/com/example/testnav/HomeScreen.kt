package com.example.testnav
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController:NavHostController) {
    Column (modifier=Modifier.fillMaxSize(),
        verticalArrangement=Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text="Home screen")
        Button(onClick = {
            navController.navigate("Detail")
        }) {
            Text("Detail")
        }
    }
}