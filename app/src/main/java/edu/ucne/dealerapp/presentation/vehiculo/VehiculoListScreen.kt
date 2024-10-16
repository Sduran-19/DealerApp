@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package edu.ucne.dealerapp.presentation.vehiculo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.dealerapp.R
import edu.ucne.dealerapp.data.remote.dto.VehiculoDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale



@Composable
fun VehiculoListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: VehiculoViewModel = hiltViewModel(),
    onClickVehiculo: (Int) -> Unit,
    onAddVehiculo: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    VehiculoListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        onClickVehiculo = onClickVehiculo,
        onAddVehiculo = onAddVehiculo,
        onDeleteVehiculo = { vehiculoId ->
            viewModel.onEvent(VehiculoUiEvent.VehiculoIdChanged(vehiculoId.toString()))
            viewModel.onEvent(VehiculoUiEvent.Delete)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun VehiculoListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: VehiculoUiState,
    onClickVehiculo: (Int) -> Unit,
    onAddVehiculo: () -> Unit,
    onDeleteVehiculo: (Int) -> Unit
) {
    val vehiculos = remember { mutableStateListOf(*uiState.vehiculos.toTypedArray()) }

    LaunchedEffect(uiState.vehiculos) {
        vehiculos.clear()
        vehiculos.addAll(uiState.vehiculos)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Vehículos",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddVehiculo
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar Nuevo Vehículo"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (uiState.error?.isNotBlank() == true) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        uiState.error?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (vehiculos.isEmpty()) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_launcher_background),
                                        contentDescription = "Lista vacía"
                                    )
                                    Text(
                                        text = "Lista vacía",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        } else {
                            items(
                                items = vehiculos,
                                key = { it.vehiculoId  }
                            ) {
                                VehiculoRow(
                                    it = it,
                                    onClickVehiculo = onClickVehiculo,
                                    onDeleteVehiculo = onDeleteVehiculo,
                                    vehiculos = vehiculos,
                                    modifier = Modifier.animateItemPlacement(tween(200))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VehiculoRow(
    it: VehiculoDto,
    onClickVehiculo: (Int) -> Unit,
    onDeleteVehiculo: (Int) -> Unit,
    vehiculos: MutableList<VehiculoDto>,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                scope.launch {
                    delay(500)
                    onDeleteVehiculo(it.vehiculoId )
                    vehiculos.remove(it)
                }
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            val backgroundColor by animateColorAsState(
                targetValue = when (swipeToDismissState.currentValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.Settled -> Color.White
                }, label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            )
        },
        modifier = modifier
    ) {
        Card(
            onClick = {
                onClickVehiculo(it.vehiculoId)
            },
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFB0BEC5)
            ),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .heightIn(min = 100.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {


                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Marca: ${it.marca}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Modelo: ${it.modelo}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Placa: ${it.placa}",
                        style = MaterialTheme.typography.bodyMedium,
                    )

                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun VehiculoListScreenPreview() {
    VehiculoListScreen(
        drawerState = DrawerState(DrawerValue.Closed),
        scope = rememberCoroutineScope(),
        onClickVehiculo = {},
        onAddVehiculo = {}
    )
}

