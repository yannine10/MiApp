package com.app.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pagos")
data class Pago(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val monto: String,
    val tarjeta: String,
    val fecha: Long = System.currentTimeMillis()
)