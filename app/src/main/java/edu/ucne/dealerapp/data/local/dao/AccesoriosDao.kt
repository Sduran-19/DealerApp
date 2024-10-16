package edu.ucne.dealerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import edu.ucne.dealerapp.data.local.entities.AccesoriosEntity

@Dao
interface AccesoriosDao {

    @Upsert
    suspend fun addAccesorio(AccesoriosEntity: AccesoriosEntity)

    @Query("""
        SELECT * FROM Accesorios
        WHERE accesorioId = :accesorioId
        LIMIT 1
    """)
    suspend fun getAccesorio(accesorioId: Int): AccesoriosEntity?

    @Delete
    suspend fun deleteAccesorio(AccesoriosEntity: AccesoriosEntity)

    @Query("SELECT * FROM Accesorios")
     fun getAccesorios(): Flow<List<AccesoriosEntity>>

}