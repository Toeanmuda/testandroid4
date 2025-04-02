package com.example.androidtest2.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtest2.data.model.Product
import com.example.androidtest2.databinding.ItemProductBinding

class ProductAdapter(private var productList: List<Product>, private val onProductClick: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.title
            binding.productPrice.text = "$${product.price}"

            // Load the product image using Glide
            Glide.with(binding.root.context)
                .load(product.image)
                .into(binding.productImage)

            // Set click listener to handle product click
            binding.root.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    // Create the ViewHolder from the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    // Bind the product data to the ViewHolder at the specified position
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    // Return the size of the product list
    override fun getItemCount(): Int = productList.size

    // Method to update the list of products in the adapter
    fun updateProductList(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()  // Notify the adapter that the data has changed
    }
}
