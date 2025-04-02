package com.example.androidtest2.ui.cartactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest2.data.model.CartItem
import com.example.androidtest2.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _totalPrice = MutableLiveData<Float>()
    val totalPrice: LiveData<Float> get() = _totalPrice

    fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = cartRepository.getCartItems()
            _totalPrice.value = cartRepository.getTotalPrice()
        }
    }

    fun removeProduct(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.removeProductFromCart(cartItem)
            loadCartItems()
        }
    }

    fun updateQuantity(cartItem: CartItem, quantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(cartItem.product, quantity)
            loadCartItems()
        }
    }
}
