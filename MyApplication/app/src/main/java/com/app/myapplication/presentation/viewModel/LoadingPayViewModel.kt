package com.app.myapplication.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.myapplication.data.Pago
import com.app.myapplication.data.repository.PagoRepository
import kotlinx.coroutines.launch

class LoadingPayViewModel(private val pagoRepository: PagoRepository) : ViewModel()  {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _cardNumber = mutableStateOf("")
    val cardNumber: State<String> get() = _cardNumber


    fun startLoading(amount: String) {
        _isLoading.value = true
        generateCardNumber()

        viewModelScope.launch {
            // Simulamos un retraso de 2 segundos para procesar el pago
            kotlinx.coroutines.delay(2000)
            savePago(amount, _cardNumber.value)
            _isLoading.value = false
        }
    }

    fun stopLoading() {
        _isLoading.value = false
    }

    private fun generateCardNumber() {
        val randomCardNumber = List(4) { (1000..9999).random() }
            .joinToString(" ") { it.toString() }
        _cardNumber.value = randomCardNumber
    }

    private suspend fun savePago(amount: String, cardNumber: String) {
        val pago = Pago(monto = amount, tarjeta = cardNumber)
        pagoRepository.insertPago(pago)
    }
}

class LoadingVMFactory(
private val repository: PagoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoadingPayViewModel(repository) as T
    }
}
