package com.example.androidtest2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest2.data.model.Product
import com.example.androidtest2.data.repository.ProductRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _user = MutableLiveData<JsonObject>()
    val user: LiveData<JsonObject> = _user

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    private val _cartItemCount = MutableLiveData<Int>()
    val cartItemCount: LiveData<Int> = _cartItemCount

    // Call this function to update the cart count
    fun updateCartItemCount() {
        viewModelScope.launch {
            val count = repository.getCartItemCount()
            _cartItemCount.postValue(count)  // Post the count to LiveData
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val response: Response<List<Product>> = repository.getProducts()
                if (response.isSuccessful) {
                    _products.postValue(response.body())
                } else {
                    _errorMessage.postValue("Error fetching products.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load products: ${e.message}")
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            try {
                val response: Response<List<String>> = repository.getCategories()
                if (response.isSuccessful) {
                    _categories.postValue(response.body())
                } else {
                    _errorMessage.postValue("Error fetching categories.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load categories: ${e.message}")
            }
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response: Response<List<Product>> = repository.getProductsByCategory(category)
                if (response.isSuccessful) {
                    _products.postValue(response.body())
                } else {
                    _errorMessage.postValue("Error fetching products by category.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load products by category: ${e.message}")
            }
        }
    }

    fun getUser(id: String) {
        viewModelScope.launch {
            try {
                val response: Response<JsonObject> = repository.getUser(id)
                if (response.isSuccessful) {
                    _user.postValue(response.body())
                } else {
                    _errorMessage.postValue("Error fetching products by category.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load products by category: ${e.message}")
            }
        }
    }
}
