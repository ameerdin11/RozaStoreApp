package com.example.roza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roza.data.CartViewModel
import com.example.roza.ui.navigation.RozaNavGraph
import com.example.roza.ui.theme.ROZATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ROZATheme {
                val cartViewModel: CartViewModel = viewModel()
                val productViewModel: com.example.roza.ui.viewmodel.ProductViewModel = viewModel()
                RozaNavGraph(cartViewModel = cartViewModel, productViewModel = productViewModel)
            }
        }
    }
}