package edu.ucne.dealerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.dealerapp.navigation.DealerNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DealerAppsTheme {
             val navHost = rememberNavController()
                val items = BuildNavigationItems()
                DealerNavHost(navHostController = navHost, items = items)
            }
        }
    }
}


fun BuildNavigationItems(): List<NavigationItem>{
    return listOf(
        NavigationItem(
            title = "Vehiculos",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Route.Vehiculos
        ),
        NavigationItem(
            title = "Accesorios",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = Route.Accesorios
        )
    )
}
@Composable
fun DealerAppsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}