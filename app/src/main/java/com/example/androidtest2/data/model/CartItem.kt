package com.example.androidtest2.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id_cart: Int = 0,
    @ColumnInfo(name = "product_id") val productId: Int,
    @Embedded val product: Product,
    var quantity: Int = 1
)