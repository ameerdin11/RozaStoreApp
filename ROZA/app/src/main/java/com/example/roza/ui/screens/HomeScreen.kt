package com.example.roza.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.roza.data.model.Product
import com.example.roza.ui.components.AnnouncementBanner
import com.example.roza.ui.components.FilterChipRow
import com.example.roza.ui.components.ProductCard
import com.example.roza.R

import com.example.roza.ui.viewmodel.ProductUiState

@Composable
fun HomeScreen(
    uiState: ProductUiState,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("New Arrivals") }
    val categories = listOf("New Arrivals", "All", "Lawn", "Chiffon", "Casual", "Formal")
    
    val filteredProducts = if (uiState is ProductUiState.Success) {
        val products = uiState.products
        if (selectedCategory == "All") {
            products
        } else if (selectedCategory == "New Arrivals") {
            products.filter { it.isNew }
        } else {
            products.filter { it.category == selectedCategory }
        }
    } else emptyList()

    Column(modifier = Modifier.fillMaxSize()) {
        AnnouncementBanner(text = "Free Delivery on orders above Rs. 10,000 | Nationwide Shipping available")
        
        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // Classic traditional Pakistani heritage background image
            AsyncImage(
                model = R.drawable.pakistani_embroidered_model,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            // Premium tint overlay matching the theme's primary color with 0.3f opacity to keep colors vibrant while ensuring text legibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = "https://roza-store.vercel.app/Logo/roza-icon-light.svg",
                    contentDescription = "ROZA Logo",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "ROZA",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Premium Pakistani Fashion",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
        
        FilterChipRow(
            chips = categories,
            selectedChip = selectedCategory,
            onChipSelected = { selectedCategory = it }
        )
        
        if (uiState is ProductUiState.Loading) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState is ProductUiState.Error) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(uiState.message, color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product.id) },
                        onAddToCart = { onAddToCart(product) }
                    )
                }
            }
        }
    }
}
