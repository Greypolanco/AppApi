package com.example.appapi.ui.cliente


import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appapi.data.remote.dto.ClienteDto
import com.example.appapi.data.repository.ClienteRepository
import com.example.appapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClienteListState(
    val isLoading: Boolean = false,
    val cliente: List<ClienteDto> = emptyList(),
    val error: String = ""
)

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    var nombres by mutableStateOf("")
    var rnc by mutableStateOf("")
    var direccion by mutableStateOf("")
    var limiteCredito by mutableStateOf(0)

    private var _state = mutableStateOf(ClienteListState())
    val state: State<ClienteListState> = _state

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    val clientes: StateFlow<Resource<List<ClienteDto>>> = clienteRepository.getCliente().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Resource.Loading()
    )

    init {
        clienteRepository.getCliente().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = ClienteListState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = ClienteListState(cliente = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ClienteListState(error = result.message ?: "Error desconocido")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun save() {
        viewModelScope.launch {
            val cliente = ClienteDto(
                nombres = nombres,
                direccion = direccion,
                rnc = rnc,
                limiteCredito = limiteCredito
            )
            clienteRepository.postCliente(cliente)
            limpiar()
        }
    }



    fun deleted(clienteid: Int, cliente: ClienteDto) {
        viewModelScope.launch {
            clienteRepository.deleteCliente(clienteid, cliente)
            limpiar()
        }
    }



    private fun limpiar() {
        nombres = ""
        rnc = ""
        direccion = ""
        limiteCredito = 0

    }
}