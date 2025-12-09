package com.app.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.myapplication.data.Pago
import com.app.myapplication.data.repository.PagoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoricalViewModel(private val repository: PagoRepository) : ViewModel() {

    private val _pagos = MutableStateFlow<List<Pago>>(emptyList())
    val pagos: StateFlow<List<Pago>> = _pagos

    init {
        loadPagos()
    }

    fun loadPagos() {
        viewModelScope.launch {
            val lista = repository.getAllPagos()
            _pagos.value = lista
        }
    }
}

class HistoryVMFactory(private val repository: PagoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoricalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoricalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}