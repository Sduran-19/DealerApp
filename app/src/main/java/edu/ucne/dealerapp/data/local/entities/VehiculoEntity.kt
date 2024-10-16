package edu.ucne.dealerapp.data.local.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vehiculoes")
data class VehiculoEntity (
    @PrimaryKey
    val vehiculoId: Int? = null,
    val marca: String = "",
    val modelo: String = "",
    val placa: String = "",
)