package com.example.roza.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun WhatsAppFAB(
    phoneNumber: String = "923347730765",
    message: String = "Hi ROZA, I have a query about your collection."
) {
    val context = LocalContext.current
    
    FloatingActionButton(
        onClick = {
            val encodedMessage = Uri.encode(message)
            val uri = Uri.parse("https://wa.me/$phoneNumber?text=$encodedMessage")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        },
        shape = CircleShape,
        containerColor = Color(0xFF25D366),
        contentColor = Color.White
    ) {
        // Using ChatBubble as placeholder for WhatsApp icon
        Icon(
            imageVector = Icons.Default.ChatBubble,
            contentDescription = "Contact on WhatsApp"
        )
    }
}
