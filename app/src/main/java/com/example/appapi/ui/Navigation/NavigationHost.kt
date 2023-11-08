package com.example.appapi.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appapi.ui.cliente.ClienteScreen
import com.example.appapi.ui.cliente.consultaCliente

@Composable
fun NavigationHost(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.ClienteScreen.route
    ){
        composable(Destination.ClienteScreen.route){
            ClienteScreen(navController = navController)
        }
        composable(Destination.ClienteConsulta.route){
            consultaCliente(navController = navController)
        }
    }
}