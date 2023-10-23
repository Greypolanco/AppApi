package com.example.appapi.data.repository

import com.example.appapi.data.remote.ClienteApi
import com.example.appapi.data.remote.dto.ClienteDto
import com.example.appapi.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ClienteRepository @Inject constructor(private val api: ClienteApi) {

    fun getCliente(): Flow<Resource<List<ClienteDto>>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val cliente = api.getCliente() //descarga las monedas de internet, se supone quedemora algo

            emit(Resource.Success(cliente)) //indicar que se cargo correctamente.
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    fun getClienteById(id: Int): Flow<Resource<ClienteDto>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val clientebyId = api.getClienteById(id) //descarga las monedas de internet, se supone quedemora algo

            emit(Resource.Success(clientebyId)) //indicar que se cargo correctamente.
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun postCliente(cliente: ClienteDto) = api.postCliente(cliente)
    suspend fun deleteCliente(id: Int, cliente: ClienteDto) = api.deletedCliente(id, cliente)
}