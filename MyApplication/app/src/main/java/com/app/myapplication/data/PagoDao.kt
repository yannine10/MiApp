package com.app.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PagoDao {
    @Insert
    suspend fun insertPago(pago: Pago)

    @Query("SELECT * FROM pagos ORDER BY fecha DESC")
    suspend fun getAllPagos(): List<Pago>

    @Query("SELECT * FROM pagos WHERE id = :id LIMIT 1")
    suspend fun getPagoById(id: Long): Pago?
}