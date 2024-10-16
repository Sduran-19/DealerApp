package edu.ucne.dealerapp.presentation.vehiculo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.dealerapp.data.remote.Resource
import edu.ucne.dealerapp.data.remote.dto.VehiculoDto
import edu.ucne.dealerapp.data.repository.VehiculoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiculoViewModel @Inject constructor(
    private val repository: VehiculoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(VehiculoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVehiculo()
    }

    private fun getVehiculo() {
        viewModelScope.launch {
            repository.getVehiculo().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                vehiculos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: VehiculoUiEvent) {
        when (event) {
            is VehiculoUiEvent.VehiculoIdChanged -> {
                _uiState.update {
                    it.copy(
                        vehiculoId = event.vehiculoId
                    )
                }
            }
            is VehiculoUiEvent.MarcaChanged -> {
                _uiState.update {
                    it.copy(
                        marca = event.marca
                    )
                }
            }
            is VehiculoUiEvent.ModeloChanged -> {
                _uiState.update {
                    it.copy(
                        modelo = event.modelo
                    )
                }
            }
            is VehiculoUiEvent.PlacaChanged -> {
                _uiState.update {
                    it.copy(
                        placa = event.placa
                    )
                }
            }
            VehiculoUiEvent.Save -> {
                viewModelScope.launch {
                    val vehiculoExistente = _uiState.value.vehiculos
                        .find { it.placa.lowercase() == _uiState.value.placa?.lowercase() }

                    if (_uiState.value.placa.isBlank()) {
                        _uiState.update {
                            it.copy(
                                error = "La placa no puede estar vacía"
                            )
                        }
                    } else if (vehiculoExistente != null) {
                        _uiState.update {
                            it.copy(
                                error = "La placa ya existe"
                            )
                        }
                    } else {
                        if (_uiState.value.vehiculoId == null) {
                            repository.addVehiculo(_uiState.value.toEntity())
                        } else {
                            repository.updateVehiculo(
                                _uiState.value.vehiculoId!!,
                                _uiState.value.toEntity()
                            )
                        }
                        _uiState.update {
                            it.copy(
                                success = true
                            )
                        }
                    }
                }
            }
            VehiculoUiEvent.Delete -> {
                viewModelScope.launch {
                    repository.deleteVehiculo(_uiState.value.vehiculoId ?: 0)
                }
            }

            is VehiculoUiEvent.SelectedVehiculo -> TODO()
        }
    }

       fun VehiculoUiState.toEntity() = VehiculoDto(
        vehiculoId = vehiculoId?.toInt() ?: 0,
        marca = marca ?: "",
        modelo = modelo ?: "",
        placa = placa ?: ""
    )
}
