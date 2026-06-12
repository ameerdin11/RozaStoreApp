package com.example.roza.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoliciesScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exchange & Return Policy", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Maintaining the exclusivity and integrity of our premium unstitched collections.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            PolicySection("1. Exchange Eligibility", "No return policy; exchange only for manufacturing defects or wrong item; report within 3 days.")
            PolicySection("2. Condition of Fabric", "Uncut, original condition, tags intact.")
            PolicySection("3. How to Request", "WhatsApp +92 334 7730765 with order number + photo/video.")
            PolicySection("4. Shipping & Logistics", "ROZA bears charges for faulty items; customer pays for other approved exchanges.")
            PolicySection("5. Processing Time", "5–7 working days; store credit if out of stock (valid 30 days).")
            PolicySection("6. Sale Articles", "Final sale; no exchange unless manufacturing defect.")

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    val msg = Uri.encode("Hi ROZA, I need help with my order regarding the exchange policy.")
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/923347730765?text=$msg"))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Contact Customer Support", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun PolicySection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
        )
    }
}
