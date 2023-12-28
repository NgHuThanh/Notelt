package com.example.testnav

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
        composable(route = "Detail/{parameter}") { backStackEntry ->
            val parameter = backStackEntry.arguments?.getString("parameter") ?: ""
            DetailScreen(navController, parameter)
        }
        composable(route="DetailScreen2"){
            DetailScreen2(navController)
        }
        composable(route="Review"){
            ReviewScreen(navController)
        }
    }
}
//navController.navigate("Review")