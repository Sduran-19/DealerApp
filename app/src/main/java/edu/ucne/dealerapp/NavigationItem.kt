package edu.ucne.dealerapp

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: edu.ucne.dealerapp.Route,
    val badgeCount: Int? = null
)

enum class Route{
     Home,
    Vehiculos,
    Accesorios
}