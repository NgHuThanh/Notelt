package com.example.testnav

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav() {
    val navController=rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){
        composable(route="Home"){
            HomeScreen(navController)
        }
        composable(route="Detail"){
            DetailScreen()
        }
    }
}