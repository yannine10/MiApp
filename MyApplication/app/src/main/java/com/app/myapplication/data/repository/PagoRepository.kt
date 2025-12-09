package com.app.myapplication.data.repository

import com.app.myapplication.data.Pago
import com.app.myapplication.data.PagoDao

class PagoRepository(private val pagoDao: PagoDao) {

    // Insertar un nuevo pago
    suspend fun insertPago(pago: Pago) {
        pagoDao.insertPago(pago)
    }

    // Obtener todos los pagos
    suspend fun getAllPagos(): List<Pago> {
        return pagoDao.getAllPagos()
    }
}