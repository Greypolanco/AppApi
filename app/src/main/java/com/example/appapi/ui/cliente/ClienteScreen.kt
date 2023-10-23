package com.example.appapi.ui.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ClienteScreen(viewModel: ClienteViewModel = hiltViewModel())
{
    val snackbarHostState = remember { SnackbarHostState() }

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

    }
}