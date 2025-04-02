package com.example.androidtest2.data.repository

import com.example.androidtest2.data.api.RetrofitInstance
import com.example.androidtest2.data.model.LoginResponse
import com.example.androidtest2.data.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    // Fungsi untuk login
    fun login(username: String, password: String, callback: (LoginResponse?) -> Unit, errorCallback: (String) -> Unit) {
        val loginData = mapOf("username" to username, "password" to password)

        RetrofitInstance.apiService.login(loginData).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    errorCallback("Login failed")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                errorCallback(t.message ?: "Unknown error")
            }
        })
    }
}
