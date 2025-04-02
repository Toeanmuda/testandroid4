package com.example.androidtest2.ui.detailproduct

import androidx.lifecycle.*
import com.example.androidtest2.data.model.Product
import com.example.androidtest2.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val cartRepository: CartRepository) :
    ViewModel() {
    private val _productAdded = MutableLiveData<Boolean>()
    val _qty = MutableLiveData<Int>()
    val productAdded: LiveData<Boolean> = _productAdded
    val qty: LiveData<Int> = _qty

    fun addToCart(product: Product, currentQuantity: Int) {
        viewModelScope.launch {

            cartRepository.addToCart(product, currentQuantity)
            _productAdded.postValue(true)
        }
    }

    fun updateQuantity(product: Product, quantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(product, quantity)
        }
    }

    fun getCartProductQuantity(productId: Int) {
        viewModelScope.launch {
            _qty.postValue(cartRepository.getProductQuantity(productId))
        }
    }


}
