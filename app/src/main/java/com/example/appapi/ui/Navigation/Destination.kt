package com.example.appapi.ui.Navigation

sealed class Destination(val route: String) {
    object ClienteConsulta: Destination("clienteConsulta")
    object ClienteScreen: Destination("clienteScreen")
}