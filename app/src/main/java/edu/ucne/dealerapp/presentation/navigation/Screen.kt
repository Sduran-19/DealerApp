package edu.ucne.dealerapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object VehiculoListScreen : Screen()

    @Serializable
    data class VehiculoScreen(val vehiculoId: Int) : Screen()

    @Serializable
    data object AccesoriosListScreen : Screen()

    @Serializable
    data class AccesoriosScreen(val accesorioId: Int) : Screen()

    @Serializable
    data object HomeScreen : Screen()

}