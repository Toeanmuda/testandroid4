package com.example.androidtest2.ui.cartactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtest2.R
import com.example.androidtest2.data.model.CartItem
import com.example.androidtest2.databinding.ActivityCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartAdapter = CartAdapter(
            emptyList(),
            onRemoveClick = { cartItem: CartItem -> cartViewModel.removeProduct(cartItem) },
            onQuantityChange = { cartItem: CartItem, quantity ->
                cartViewModel.updateQuantity(
                    cartItem,
                    quantity
                )
            }
        )
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }

        cartViewModel.cartItems.observe(this) { cartItems ->
            cartAdapter = CartAdapter(
                cartItems,
                onRemoveClick = { cartItem: CartItem -> cartViewModel.removeProduct(cartItem) },
                onQuantityChange = { cartItem: CartItem, quantity ->
                    cartViewModel.updateQuantity(
                        cartItem,
                        quantity
                    )
                }
            )
            binding.cartRecyclerView.adapter = cartAdapter
        }

        cartViewModel.totalPrice.observe(this) { total ->
            binding.totalPriceText.text = "Total:$total"
        }

        cartViewModel.loadCartItems()

        binding.checkoutButton.setOnClickListener {
            showOrderSummaryBottomSheet()
        }
    }

    private fun showOrderSummaryBottomSheet() {
        val bottomSheetFragment = OrderSummaryBottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, "bottomSheetFragment.tag")
    }
}
