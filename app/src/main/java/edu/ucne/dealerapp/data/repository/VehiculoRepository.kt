package edu.ucne.dealerapp.data.repository

import edu.ucne.dealerapp.data.remote.RemoteDataSource
import edu.ucne.dealerapp.data.remote.Resource
import edu.ucne.dealerapp.data.remote.dto.VehiculoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class VehiculoRepository @Inject constructor (
    private val remoteDataSource: RemoteDataSource

){
    suspend fun addVehiculo(vehiculoDto: VehiculoDto) = remoteDataSource.addVehiculo(vehiculoDto)

    suspend fun getVehiculo(vehiculoId: Int) = remoteDataSource.getVehiculo(vehiculoId)

    suspend fun deleteVehiculo(vehiculoId: Comparable<*>) = remoteDataSource.deleteVehiculo(vehiculoId)

    suspend fun updateVehiculo(vehiculoId: String, toEntity: VehiculoDto) = remoteDataSource.updateVehiculo(vehiculoId)

    suspend fun getVehiculo(): Flow<Resource<List<VehiculoDto>>> = flow{
        try {
            emit(Resource.Loading())
            val vehiculos = remoteDataSource.getVehiculo()

            emit(Resource.Success(vehiculos))
    }
        catch (e: HttpException){
            emit(Resource.Error(e.message ?: "Error Desconocido ${e.message}"))
        }
        catch (e: Exception){
            emit(Resource.Error(e.message ?: "Error Desconocido ${e.message}"))
        }

    }



}