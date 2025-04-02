package com.example.androidtest2.data.repository

import com.example.androidtest2.data.api.ApiService
import com.example.androidtest2.data.dao.CartDao
import com.example.androidtest2.data.model.Product
import com.google.gson.JsonObject
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService, private val cartDao: CartDao) {

    suspend fun getProducts(): Response<List<Product>> {
        return apiService.getProducts()
    }

    suspend fun getCategories(): Response<List<String>> {
        return apiService.getCategories()
    }

    suspend fun getProductsByCategory(category: String): Response<List<Product>> {
        return apiService.getProductsByCategory(category)
    }

    suspend fun getUser(category: String): Response<JsonObject> {
        return apiService.getUser(category)
    }

    suspend fun getCartItemCount(): Int {
        return cartDao.countCartItems()
    }
}
