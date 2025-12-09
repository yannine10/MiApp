package com.app.myapplication.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PayViewModel: ViewModel() {

    private val _input = mutableStateOf("")
    val input: State<String> get() = _input

    fun onNumberClick(number: String) {
        _input.value += number
    }

    fun onClearClick() {
        if (_input.value.isNotEmpty()) {
            _input.value = _input.value.dropLast(1)
        }
    }

    fun onAcceptClick(): String {
        return _input.value
    }

}