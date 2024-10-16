package edu.ucne.dealerapp.presentation.vehiculo

import edu.ucne.dealerapp.data.remote.dto.VehiculoDto

data class VehiculoUiState (
    val vehiculoId: String = "",
    val vehiculos: List<VehiculoDto> = emptyList(),
    val marca: String = "",
    val modelo: String = "",
    val placa: String = "",
    val success: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
) {

}
