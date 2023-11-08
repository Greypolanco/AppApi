package com.example.appapi.ui.cliente

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.appapi.data.remote.dto.ClienteDto
import com.example.appapi.ui.Navigation.Destination
import com.example.appapi.util.Resource

@Composable
fun consultaCliente(
    viewModel: ClienteViewModel = hiltViewModel(),
    navController: NavController
){
    val clientes by viewModel.clientes.collectAsStateWithLifecycle()
    Text(text = "Historial de Clientes", style = MaterialTheme.typography.titleMedium)
    LazyColumn( modifier = Modifier.fillMaxWidth()) {
        items(clientes.data ?: emptyList()){ clientDto ->
            consultaCLienteItem(clienteDto = clientDto, viewModel = viewModel)
        }
    }
    OutlinedButton(onClick = { navController.navigate(route = Destination.ClienteScreen.route)}) {
        Text(text = "Pa atra")
    }

}

@Composable
fun consultaCLienteItem(clienteDto: ClienteDto, viewModel: ClienteViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(13.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(13.dp)
        ){
            Text(text ="Nombre:" + clienteDto.nombres)
            Text(text = "RNC: " + clienteDto.rnc)
            Text(text = "Dirección " + clienteDto.direccion)
            Text(text = "Limite De Crédito es Requerido" + clienteDto.limiteCredito)
        }
        Button(
            onClick = {
                clienteDto.clienteId?.let { viewModel.deleted(it, clienteDto) }
            }
        ) {
            Text(text = "Eliminar")
        }
    }
}