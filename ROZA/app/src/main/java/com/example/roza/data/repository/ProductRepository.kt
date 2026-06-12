package com.example.roza.data.repository

import com.example.roza.data.api.RetrofitInstance
import com.example.roza.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {
    private val demoProducts = listOf(
        Product(
            id = "demo_lawn_1",
            name = "Zari Embroidered Lawn",
            price = 8500,
            images = listOf("https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?q=80&w=800"),
            category = "Lawn",
            description = "A luxurious 3-piece embroidered lawn suit featuring intricate zari border detailing, pure chiffon dupatta, and dyed cambric trousers. Ideal for timeless elegance.",
            inStock = true,
            isVisible = true
        ),
        Product(
            id = "demo_formal_1",
            name = "Royal Silk Chiffon",
            price = 12500,
            images = listOf("https://images.unsplash.com/photo-1601924994987-69e26d50dc26?q=80&w=800"),
            category = "Formal",
            description = "Crafted from premium raw silk and pure chiffon, this luxury ensemble boasts exquisite hand-embroidered tilla work and hand-stitched detailing for a classic royal look.",
            inStock = true,
            isVisible = true
        ),
        Product(
            id = "demo_chiffon_1",
            name = "Classic Ivory Organza",
            price = 9800,
            images = listOf("https://images.unsplash.com/photo-1595777457583-95e059d581b8?q=80&w=800"),
            category = "Chiffon",
            description = "An ethereal ivory organza shirt with delicate thread embroidery, hand-crafted pearl embellishments, matching silk slip, and a block-printed dupatta.",
            inStock = true,
            isVisible = true
        ),
        Product(
            id = "demo_casual_1",
            name = "Vintage Cotton Kurta",
            price = 4500,
            images = listOf("https://images.unsplash.com/photo-1607345366928-199ea26cfe3e?q=80&w=800"),
            category = "Casual",
            description = "Comfort meets classic style with this 100% premium breathable cotton casual kurta featuring refined neckline embroidery and comfortable modern fit.",
            inStock = true,
            isVisible = true
        )
    )

    suspend fun fetchProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            val apiProducts = try {
                val allProducts = RetrofitInstance.api.getProducts()
                allProducts.filter { it.isVisible }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
            apiProducts + demoProducts
        }
    }
}
