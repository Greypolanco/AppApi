package com.example.appapi.ui.cliente

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appapi.data.remote.dto.ClienteDto
import com.example.appapi.util.Resource
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ClienteScreen(viewModel: ClienteViewModel = hiltViewModel())
{
    val snackbarHostState = remember { SnackbarHostState() }
    val clientes by viewModel.clientes.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Cliente guardado",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    val keyBoardControlle = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        //Nombre
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = viewModel.nombres  , onValueChange ={viewModel.nombres = it},
            label = { Text(text = "Nombres")},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        if(viewModel.nombresInvalido == false){
        Text(text = "Nombre es Requerido", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        //RNC
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = viewModel.rnc,
            onValueChange = {viewModel.rnc = it},
            label = { Text(text = "RNC")},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        if(viewModel.rncInvalidado == false){
            Text(text = "RNC es Requerido", color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        //Dirección
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = viewModel.direccion,
            onValueChange = {viewModel.direccion = it},
            label = { Text(text = "Dirección")},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        if(viewModel.direccionInvalida == false){
            Text(text = "Dirección es Requerida", color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        //Limite De Crédito
        OutlinedTextField(
            value = viewModel.limiteCredito.toString(),
            onValueChange = {
                viewModel.limiteCredito = it.toIntOrNull() ?: 0
            },
            label = { Text(text = "Limite De Crédito")},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            )
        )
        if(viewModel.limiteCreditoInvalida == false){
            Text(text = "Limite De Crédito es Requerido", color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = {
            keyBoardControlle?.hide()
            if(viewModel.validar()){
                viewModel.save()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
            Text(text = "Guardar")
        }
        Spacer(modifier = Modifier.width(12.dp))
        //consultaCliente()
    consultaCliente(clientes =clientes , viewModel =viewModel )
    }
}

@Composable
fun consultaCliente(clientes: Resource<List<ClienteDto>>, viewModel: ClienteViewModel){
    Text(text = "Historial de Clientes", style = MaterialTheme.typography.titleMedium)
    LazyColumn( modifier = Modifier.fillMaxWidth()) {
        items(clientes.data ?: emptyList()){ clientDto ->
            consultaCLienteItem(clienteDto = clientDto, viewModel = viewModel)
        }
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