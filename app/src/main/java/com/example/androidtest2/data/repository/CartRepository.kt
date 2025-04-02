package com.example.androidtest2.data.repository

import com.example.androidtest2.data.dao.CartDao
import com.example.androidtest2.data.model.CartItem
import com.example.androidtest2.data.model.Product
import javax.inject.Inject


class CartRepository @Inject constructor(private val cartDao: CartDao) {


    suspend fun addToCart(product: Product, currentQuantity: Int) {
        val cartItem = cartDao.getProductById(product.id)
        if (cartItem != null) {
            // If product already in cart, increase quantity
            cartItem.quantity = currentQuantity
            cartDao.update(cartItem)
        } else {
            // If product not in cart, add with quantity 1
            val cartItem = CartItem(productId = product.id, product = product, quantity = currentQuantity)
            cartDao.insert(cartItem)
        }
    }

    suspend fun updateQuantity(product: Product, quantity: Int) {
        val cartItem = cartDao.getProductById(product.id)
        cartItem?.let {
            it.quantity = quantity
            cartDao.update(it)
        }
    }

    suspend fun getCartItems(): List<CartItem> {
        return cartDao.getAllProducts()
    }

    suspend fun removeProductFromCart(cartItem: CartItem) {
        cartDao.delete(cartItem)
    }

    suspend fun getProductQuantity(productId: Int): Int {
        val item = cartDao.getProductById(productId)
        if (item == null) {
            return 1;
        } else {
            return item.quantity
        }
    }

    suspend fun getTotalPrice(): Float {
        return cartDao.getTotalPrice()
    }
}
