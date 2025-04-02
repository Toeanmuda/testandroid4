package com.example.androidtest2.ui.cartactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtest2.data.model.CartItem
import com.example.androidtest2.databinding.ItemCartBinding

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onRemoveClick: (CartItem) -> Unit,
    private val onQuantityChange: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.productTitle.text = cartItem.product.title
            binding.productQuantity.text = "QTY: " + cartItem.quantity.toString()
            binding.productQuantityText.text =  cartItem.quantity.toString()

            binding.productPrice.text = "$${cartItem.product.price}"

            Glide.with(binding.root.context).load(cartItem.product.image).into(binding.productImage)

            binding.removeButton.setOnClickListener {
                onRemoveClick(cartItem)
            }

            binding.increaseButton.setOnClickListener {
                val newQuantity = cartItem.quantity + 1
                onQuantityChange(cartItem, newQuantity)
            }

            binding.decreaseButton.setOnClickListener {
                val newQuantity = if (cartItem.quantity > 1) cartItem.quantity - 1 else 1
                onQuantityChange(cartItem, newQuantity)
            }
        }
    }
}
