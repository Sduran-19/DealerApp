package edu.ucne.dealerapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {

    @Serializable
    object HomeScreen : Screen("home")

    @Serializable
    object VehiculoListScreen : Screen("vehiculo_list")

    @Serializable
    data class VehiculoScreen(val vehiculoId: Int) : Screen("vehiculo_screen/$vehiculoId")

    @Serializable
    object AccesoriosListScreen : Screen("accesorios_list")

    @Serializable
    data class AccesoriosScreen(val accesorioId: Int) : Screen("accesorios_screen/$accesorioId")
}
