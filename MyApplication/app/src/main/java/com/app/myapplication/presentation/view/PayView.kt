package com.app.myapplication.presentation.view

import android.os.Handler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.myapplication.presentation.viewModel.PayViewModel

@Composable
fun PayView(navController: NavHostController, moneyInputViewModel: PayViewModel = viewModel()) {
    val input by moneyInputViewModel.input
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        OutlinedTextField(
            value = input,
            onValueChange = {},
            label = { Text("Monto a cobrar") },
            placeholder = { Text("0") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(60.dp),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color(0xFFBB86FC),
                focusedLabelColor = Color(0xFF6200EE),
                unfocusedLabelColor = Color(0xFFBB86FC)
            )
        )

        NumericKeyboard(onButtonPressed = { button ->
            when (button) {
                "C" -> moneyInputViewModel.onClearClick()
                "Aceptar" -> {
                    val amount = moneyInputViewModel.onAcceptClick()

                    // Validar que el monto sea mayor a cero
                    if ((amount.toIntOrNull() ?: 0) <= 0) {
                        errorMessage = "El monto debe ser mayor que cero."
                    } else {
                        errorMessage = "" 
                        navController.navigate("loading/$amount")
                        Handler().postDelayed({
                            navController.navigate("confirmation/$amount")
                        }, 2000)
                    }
                }
                else -> {
                    moneyInputViewModel.onNumberClick(button)
                }
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amount = moneyInputViewModel.onAcceptClick()

                if (amount.toIntOrNull() ?: 0 <= 0) {
                    errorMessage = "El monto debe ser mayor que cero."
                } else {
                    errorMessage = ""
                    navController.navigate("loading/$amount")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = input.isNotEmpty()
        ) {
            Text("Aceptar", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun NumericKeyboard(onButtonPressed: (String) -> Unit) {
    val rows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "C")
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { buttonText ->
                    if (buttonText.isNotEmpty()) {
                        NumericButton(text = buttonText, onClick = {
                            onButtonPressed(buttonText)
                        })
                    } else {
                        Spacer(modifier = Modifier.size(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NumericButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp),
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
    ) {
        if (text == "C") {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Borrar",
                tint = Color.White
            )
        } else {
            Text(text, style = MaterialTheme.typography.bodyLarge, color = Color.White)
        }
    }
}
