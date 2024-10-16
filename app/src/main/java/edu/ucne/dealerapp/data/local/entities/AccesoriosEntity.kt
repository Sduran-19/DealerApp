package edu.ucne.dealerapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Accesorios")
data class AccesoriosEntity (
    @PrimaryKey
    val accesorioId: Int? = null,
    val descripcion: String = ""

)