package com.example.roza.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("images") val images: List<String>?,
    @SerializedName("category") val category: String?,
    @SerializedName("description") val description: String,
    @SerializedName("inStock") val inStock: Boolean,
    @SerializedName("isVisible") val isVisible: Boolean
) {
    val imageUrl: String
        get() = if (!images.isNullOrEmpty()) images[0] else "https://via.placeholder.com/800x1200?text=No+Image"

    val isNew: Boolean
        get() = category?.equals("New", ignoreCase = true) == true || category?.equals("New Arrivals", ignoreCase = true) == true
}
