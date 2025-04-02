package com.example.androidtest2.data.api

import com.example.androidtest2.data.model.LoginResponse
import com.example.androidtest2.data.model.Product
import com.google.gson.JsonObject

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    fun login(@Body loginData: Map<String, String>): Call<LoginResponse>

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<Product>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): Response<JsonObject>

}
