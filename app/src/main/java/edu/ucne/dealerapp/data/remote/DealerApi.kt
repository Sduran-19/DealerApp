package edu.ucne.dealerapp.data.remote

import edu.ucne.dealerapp.data.remote.dto.AccesoriosDto
import edu.ucne.dealerapp.data.remote.dto.VehiculoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DealerApi {

    //Vehiculos
    @POST("api/Vehiculoes")
    suspend fun addVehiculo(@Body vehiculoDto: VehiculoDto?): VehiculoDto

    @GET("api/Vehiculoes")
    suspend fun getVehiculo(): List<VehiculoDto>

    @GET("api/Vehiculoes/{id}")
    suspend fun getVehiculo(@Path("id") id: Int): VehiculoDto

    @PUT("api/Vehiculoes/{id}")
    suspend fun updateVehiculo(@Path("id") id: String): VehiculoDto

    @DELETE("api/Vehiculoes/{id}")
    suspend fun deleteVehiculo(@Path("id") id: Comparable<*>)




    //Accesorios
    @GET("api/Accesorios")
    suspend fun getAccesorio(): List<AccesoriosDto>

    @GET("api/Accesorios/{id}")
    suspend fun getAccesorio(@Path("id") id: Int): AccesoriosDto

    @PUT("api/Accesorios/{id}")
    suspend fun updateAccesorio(@Path("id") id: Int): AccesoriosDto

    @DELETE("api/Accesorios/{id}")
    suspend fun deleteAccesorio(@Path("id") id: Int)


    @POST("api/Accesorios")
    suspend fun addAccesorio(@Body accesorioDto: AccesoriosDto?): AccesoriosDto

}