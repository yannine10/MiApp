package com.app.myapplication.presentation.view

import android.os.Handler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.myapplication.presentation.viewModel.LoadingPayViewModel

@Composable
fun LoadingPayView(navController: NavController, amount: String, loadingViewModel: LoadingPayViewModel) {
    val isLoading by loadingViewModel.isLoading
    val cardNumber by loadingViewModel.cardNumber

    LaunchedEffect(Unit) {
        loadingViewModel.startLoading(amount)
        Handler().postDelayed({
            loadingViewModel.stopLoading()
            navController.navigate("confirmation/$amount/$cardNumber")
        }, 2000)
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Procesando...", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Número de tarjeta: $cardNumber",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
