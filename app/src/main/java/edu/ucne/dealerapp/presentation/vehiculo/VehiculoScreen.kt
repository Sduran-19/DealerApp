@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.dealerapp.presentation.vehiculo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.dealerapp.ui.theme.DealerAppTheme
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun VehiculoScreen(
    vehiculoId: Int,
    viewModel: VehiculoViewModel = hiltViewModel(),
    goVehiculoList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    VehiculoBodyScreen(
        vehiculoId = vehiculoId,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goVehiculoList = goVehiculoList
    )
}

@Composable
private fun VehiculoBodyScreen(
    vehiculoId: Int,
    uiState: VehiculoUiState,
    onEvent: (VehiculoUiEvent) -> Unit,
    goVehiculoList: () -> Unit
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    LaunchedEffect(key1 = true, key2 = uiState.success) {
        onEvent(VehiculoUiEvent.SelectedVehiculo(vehiculoId))

        if (uiState.success)
            goVehiculoList()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (vehiculoId == 0) "Crear Vehículo" else "Modificar Vehículo",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = goVehiculoList
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Ir hacia lista de vehículos"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {



                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            label = { Text("Marca") },
                            value = uiState.marca ?: "",
                            onValueChange = { onEvent(VehiculoUiEvent.MarcaChanged(it)) },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .focusRequester(focusRequester)
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    onEvent(VehiculoUiEvent.Save)
                                }
                            )
                        )
                        uiState.error?.let {
                            Text(text = it, color = Color.Red)
                        }

                        OutlinedTextField(
                            label = { Text("Modelo") },
                            value = uiState.modelo ?: "",
                            onValueChange = { onEvent(VehiculoUiEvent.ModeloChanged(it)) },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp)),
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    onEvent(VehiculoUiEvent.Save)
                                }
                            )
                        )
                        uiState.error?.let {
                            Text(text = it, color = Color.Red)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { focusRequester.requestFocus() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Empezar formulario"
                            )
                            Text("Empezar a llenar")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        OutlinedButton(
                            onClick = { onEvent(VehiculoUiEvent.Save) }
                        ) {
                            Icon(
                                imageVector = if (vehiculoId == 0) Icons.Default.Add else Icons.Default.Done,
                                contentDescription = "Guardar Vehículo"
                            )
                            Text(text = if (vehiculoId == 0) "Crear Vehículo" else "Modificar Vehículo")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun VehiculoScreenPreview() {
    DealerAppTheme {
        VehiculoScreen(vehiculoId = 0, goVehiculoList = {})
    }
}
