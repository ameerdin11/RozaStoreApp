package com.example.roza.data.api

import com.example.roza.data.model.Product
import retrofit2.http.GET

interface ProductApiService {
    @GET("api/products")
    suspend fun getProducts(): List<Product>
}
