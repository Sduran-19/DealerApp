package edu.ucne.dealerapp.presentation.vehiculo

sealed interface VehiculoUiEvent {
    data class VehiculoIdChanged(val vehiculoId: String) : VehiculoUiEvent
    data class MarcaChanged(val marca: String) : VehiculoUiEvent
    data class ModeloChanged(val modelo: String) : VehiculoUiEvent
    data class PlacaChanged(val placa: String) : VehiculoUiEvent
    data class SelectedVehiculo(val vehiculoId: Int) : VehiculoUiEvent

    object Save : VehiculoUiEvent
    object Delete : VehiculoUiEvent


}