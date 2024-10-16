package edu.ucne.dealerapp.data.remote

import edu.ucne.dealerapp.data.remote.dto.AccesoriosDto
import edu.ucne.dealerapp.data.remote.dto.VehiculoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val dealerApi: DealerApi

){
    //Vehiculos
    suspend fun addVehiculo(vehiculoDto: VehiculoDto) = dealerApi.addVehiculo(vehiculoDto)

    suspend fun getVehiculo() = dealerApi.getVehiculo()

    suspend fun getVehiculo(vehiculoId: Int) = dealerApi.getVehiculo(vehiculoId)

    suspend fun deleteVehiculo(vehiculoId: Comparable<*>) = dealerApi.deleteVehiculo(vehiculoId)

    suspend fun updateVehiculo(vehiculoId: String) = dealerApi.updateVehiculo(vehiculoId)

    //Accesorios

    suspend fun addAccesorio(accesorioDto: AccesoriosDto) = dealerApi.addAccesorio(accesorioDto)

    suspend fun getAccesorio() = dealerApi.getAccesorio()

    suspend fun getAccesorio(accesorioId: Int) = dealerApi.getAccesorio(accesorioId)

    suspend fun deleteAccesorio(accesorioId: Int) = dealerApi.deleteAccesorio(accesorioId)

    suspend fun updateAccesorio(accesorioId: Int) = dealerApi.updateAccesorio(accesorioId)





}