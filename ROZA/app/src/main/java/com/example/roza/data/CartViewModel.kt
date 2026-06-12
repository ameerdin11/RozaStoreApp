package com.example.roza.data

import androidx.lifecycle.ViewModel
import com.example.roza.data.model.CartItem
import com.example.roza.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product, quantity: Int = 1) {
        _cartItems.update { items ->
            val existingItem = items.find { it.product.id == product.id }
            if (existingItem != null) {
                items.map {
                    if (it.product.id == product.id) {
                        it.copy(quantity = it.quantity + quantity)
                    } else {
                        it
                    }
                }
            } else {
                items + CartItem(product, quantity)
            }
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.update { items ->
            items.filter { it.product.id != productId }
        }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
            return
        }
        _cartItems.update { items ->
            items.map {
                if (it.product.id == productId) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            }
        }
    }

    fun getTotal(): Int {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun getDeliveryFee(): Int {
        val total = getTotal()
        if (total == 0) return 0
        return if (total >= 10000) 0 else 200
    }

    fun buildWhatsAppMessage(): String {
        val items = _cartItems.value
        if (items.isEmpty()) return "Hi ROZA, I have a query."
        
        val sb = java.lang.StringBuilder()
        sb.append("Hi ROZA, I would like to place an order:\n\n")
        items.forEach { item ->
            sb.append("- ${item.product.name} (x${item.quantity}) - Rs. ${item.product.price * item.quantity}\n")
        }
        sb.append("\nSubtotal: Rs. ${getTotal()}")
        sb.append("\nDelivery: Rs. ${getDeliveryFee()}")
        sb.append("\n*Total: Rs. ${getTotal() + getDeliveryFee()}*")
        
        return sb.toString()
    }
}
