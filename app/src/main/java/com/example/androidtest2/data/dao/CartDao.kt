package com.example.androidtest2.data.dao

import androidx.room.*
import com.example.androidtest2.data.model.CartItem
import com.example.androidtest2.data.model.Product

@Dao
interface CartDao {

    @Insert
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllProducts(): List<CartItem>

    @Query("SELECT * FROM cart_items WHERE product_id = :productId")
    suspend fun getProductById(productId: Int): CartItem?

    @Update
    suspend fun update(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT SUM(price * quantity) FROM cart_items")
    suspend fun getTotalPrice() : Float

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun countCartItems(): Int
}
