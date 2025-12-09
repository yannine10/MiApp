package com.app.myapplication.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.myapplication.data.AppDatabase
import com.app.myapplication.data.repository.PagoRepository
import com.app.myapplication.presentation.view.ConfirmationView
import com.app.myapplication.presentation.view.HistoricalSView
import com.app.myapplication.presentation.view.LoadingPayView
import com.app.myapplication.presentation.view.PayView
import com.app.myapplication.presentation.viewModel.HistoricalViewModel
import com.app.myapplication.presentation.viewModel.HistoryVMFactory
import com.app.myapplication.presentation.viewModel.LoadingPayViewModel
import com.app.myapplication.presentation.viewModel.LoadingVMFactory
import com.app.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(applicationContext)
        val repository = PagoRepository(db.pagoDao())

        setContent {
            MyApplicationTheme {
                // Llama a la función con la configuración del NavHost
                MyApp(repository)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(repository: PagoRepository) {
    val navController = rememberNavController() // Controlador de navegación
    // Configuración de la navegación
    Scaffold(
        bottomBar = {
            // Barra de navegación inferior
            NavigationBar {
                NavigationBarItem(
                    selected = false, // Cambia este valor según la navegación actual
                    onClick = { navController.navigate("pay") },
                    icon = { Icon(Icons.Rounded.Done, contentDescription = "pay") },
                    label = { Text("Cobrar") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("historical") },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "historical") },
                    label = { Text("Historial") }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "pay") {
            composable("pay") {
                PayView(navController = navController)
            }
            composable("historical") {backStackEntry->
                // Crear ViewModel usando factory
                val historyVM: HistoricalViewModel = viewModel(
                    backStackEntry,
                    factory = HistoryVMFactory(repository)
                )
                HistoricalSView(vm = historyVM)
            }
            composable("loading/{amount}") { backStackEntry ->
                val vm: LoadingPayViewModel = viewModel(
                    backStackEntry,
                    factory = LoadingVMFactory(repository)
                )
                val amount = backStackEntry.arguments?.getString("amount") ?: "0"
                LoadingPayView(navController = navController, amount = amount, loadingViewModel = vm)
            }
            composable("confirmation/{amount}/{cardNumber}") { backStackEntry ->
                val amount = backStackEntry.arguments?.getString("amount") ?: "0"
                val cardNumber = backStackEntry.arguments?.getString("cardNumber") ?: "0000 0000 0000 0000"
                ConfirmationView(navController = navController, amount = amount, cardNumber = cardNumber)
            }

        }
    }
}

