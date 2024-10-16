package edu.ucne.dealerapp.data.repository

import edu.ucne.dealerapp.data.remote.RemoteDataSource
import edu.ucne.dealerapp.data.remote.Resource
import edu.ucne.dealerapp.data.remote.dto.AccesoriosDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AccesoriosRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    suspend fun addAccesorio(accesorioDto: AccesoriosDto) = remoteDataSource.addAccesorio(accesorioDto)

    suspend fun getAccesorio(accesorioId: Int) = remoteDataSource.getAccesorio(accesorioId)

    suspend fun deleteAccesorio(accesorioId: Int) = remoteDataSource.deleteAccesorio(accesorioId)

    suspend fun updateAccesorio(accesorioId: Int) = remoteDataSource.updateAccesorio(accesorioId)


    suspend fun getAccesorio(): Flow<Resource<List<AccesoriosDto>>> = flow{
        try {
            emit(Resource.Loading())
            val accesorios = remoteDataSource.getAccesorio()
            emit(Resource.Success(accesorios))

    } catch (e: HttpException){
        emit(Resource.Error(e.message ?: "Error Desconocido ${e.message}"))

        } catch (e: Exception){
            emit(Resource.Error(e.message ?: "Error Desconocido ${e.message}"))
        }

        }
}