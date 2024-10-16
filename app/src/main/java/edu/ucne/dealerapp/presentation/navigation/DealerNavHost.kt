package edu.ucne.dealerapp.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.dealerapp.NavigationItem
import edu.ucne.dealerapp.Route
import edu.ucne.dealerapp.presentation.home.HomeScreen
import edu.ucne.dealerapp.presentation.vehiculo.VehiculoListScreen
import edu.ucne.dealerapp.presentation.vehiculo.VehiculoScreen
import edu.ucne.dealerapp.presentation.navigation.Screen
import edu.ucne.dealerapp.ui.theme.DealerAppTheme
import kotlinx.coroutines.launch

@Composable
fun DealerNavHost(
    navHostController: NavHostController,
    items: List<NavigationItem>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "   Menú",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            when (item.route) {
                                Route.Home -> navHostController.navigate(Screen.HomeScreen)
                                Route.Vehiculos -> navHostController.navigate(Screen.VehiculoListScreen)
                                Route.Accesorios -> navHostController.navigate(Screen.AccesoriosListScreen)

                            }
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.HomeScreen
        ) {
            composable<Screen.VehiculoListScreen> {
                VehiculoListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickVehiculo = { vehiculoId ->
                        navHostController.navigate(Screen.VehiculoScreen(vehiculoId))
                    },
                    onAddVehiculo = {
                        navHostController.navigate(Screen.VehiculoScreen(0))
                    }
                )
            }
            composable<Screen.VehiculoScreen> { argumentos ->
                val vehiculoId = argumentos.toRoute<Screen.VehiculoScreen>().vehiculoId
                VehiculoScreen(
                    vehiculoId = vehiculoId,
                    goVehiculoList = {
                        navHostController.navigate(Screen.VehiculoListScreen)
                    }
                )
            }
            /*
            composable<Screen.AccesoriosListScreen> {
                AccesoriosListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onClickAccesorio = { accesorioId ->
                        navHostController.navigate(Screen.AccesoriosScreen.createRoute(accesorioId))
                    },
                    onAddAccesorio = {
                        navHostController.navigate(Screen.AccesoriosScreen.createRoute(0))
                    }
                )
            }
            composable<Screen.AccesoriosScreen> { navBackStackEntry ->
                val accesorioId = navBackStackEntry.arguments?.getInt("accesorioId") ?: 0
                AccesoriosScreen(
                    accesorioId = accesorioId,
                    goAccesoriosList = {
                        navHostController.navigate(Screen.AccesoriosListScreen.route)
                    }
                )
            }
            */
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DealerNavHostPreview() {
    DealerAppTheme {
        DealerNavHost(
            navHostController = rememberNavController(),
            items = listOf()
        )
    }
}
