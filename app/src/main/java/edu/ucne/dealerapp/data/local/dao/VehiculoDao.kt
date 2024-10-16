package edu.ucne.dealerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.dealerapp.data.local.entities.VehiculoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface VehiculoDao {
    @Upsert
    suspend fun addVehiculo(VehiculoEntity: VehiculoEntity)

    @Query("""
        SELECT * FROM Vehiculoes
        WHERE vehiculoId = :vehiculoId
        LIMIT 2
    """)
    suspend fun getVehiculo(vehiculoId: Int): VehiculoEntity?

    @Delete
    suspend fun deleteVehiculo(VehiculoEntity: VehiculoEntity)

    @Query("SELECT * FROM Vehiculoes")
     fun getVehiculos(): Flow<List<VehiculoEntity>>

}