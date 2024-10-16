package edu.ucne.dealerapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.dealerapp.data.local.dao.AccesoriosDao
import edu.ucne.dealerapp.data.local.dao.VehiculoDao
import edu.ucne.dealerapp.data.local.entities.AccesoriosEntity
import edu.ucne.dealerapp.data.local.entities.VehiculoEntity


@Database(
    entities = [
        VehiculoEntity::class,
        AccesoriosEntity::class
    ],
    version = 1

)
abstract class DealerDb: RoomDatabase() {
    abstract val vehiculoDao: VehiculoDao
    abstract val accesoriosDao: AccesoriosDao

}