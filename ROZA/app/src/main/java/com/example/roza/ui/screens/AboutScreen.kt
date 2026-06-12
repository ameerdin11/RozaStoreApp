package com.example.roza.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.roza.ui.components.ValuePillarCard

@Composable
fun AboutScreen(onViewPolicies: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Timeless Elegance For The Modern Soul",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Born from a passion for premium craftsmanship and authentic Pakistani heritage, ROZA brings you curated fashion that transcends trends.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        ValuePillarCard(
            icon = "✨",
            title = "Premium Quality",
            description = "We source the finest fabrics and materials to ensure every piece feels as good as it looks."
        )
        Spacer(modifier = Modifier.height(16.dp))
        ValuePillarCard(
            icon = "🌿",
            title = "Ethical Craft",
            description = "Our production processes respect both the artisans and the environment."
        )
        Spacer(modifier = Modifier.height(16.dp))
        ValuePillarCard(
            icon = "🎨",
            title = "Unique Design",
            description = "Exclusivity is at the heart of our collections, blending tradition with modern flair."
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        Text("Contact Information", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Contact Rows
        ContactRow(icon = Icons.Default.Call, text = "+92 334 7730765") {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+923347730765"))
            context.startActivity(intent)
        }
        ContactRow(icon = Icons.Default.LocationOn, text = "Sukkur, Pakistan") {}
        ContactRow(icon = Icons.Default.Schedule, text = "Monday–Saturday, 10:00 AM – 8:00 PM") {}
        
        // Instagram (using text button as a placeholder for an icon)
        TextButton(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/wearby_roza"))
            context.startActivity(intent)
        }) {
            Text("@wearby_roza on Instagram", color = MaterialTheme.colorScheme.secondary)
        }

        Spacer(modifier = Modifier.height(32.dp))
        OutlinedButton(
            onClick = onViewPolicies,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("View Exchange & Return Policy", color = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.width(16.dp))
        TextButton(onClick = onClick) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}
