package com.example.androidtest2.ui.detailproduct

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.androidtest2.data.model.Product
import com.example.androidtest2.databinding.ActivityProductDetailBinding
import com.example.androidtest2.ui.cartactivity.CartActivity
import com.example.androidtest2.ui.cart.CartBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    private var currentQuantity: Int = 1
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getParcelableExtra("product")!!

        // Set product details
        binding.productTitle.text = product.title
        binding.productPrice.text = "$${product.price}"
        binding.productDescription.text = product.description
        Glide.with(this).load(product.image).into(binding.productImage)

        checkCartForProduct()

        binding.quantityText.text = currentQuantity.toString()

        binding.addToCartButton.setOnClickListener {
            productDetailViewModel.addToCart(product, currentQuantity)
        }

        binding.increaseQuantityButton.setOnClickListener {
            if (currentQuantity < 10) {
                currentQuantity++
                binding.quantityText.text = currentQuantity.toString()
            }
        }

        binding.decreaseQuantityButton.setOnClickListener {
            if (currentQuantity > 1) {
                currentQuantity--
                binding.quantityText.text = currentQuantity.toString()
            }
        }

        productDetailViewModel.productAdded.observe(this) { added ->
            if (added) {
                showCartBottomSheet()
            }
        }
    }

    private fun checkCartForProduct() {
        productDetailViewModel.getCartProductQuantity(productId = product.id)
        productDetailViewModel.qty.observe(this) { quantity ->
            if (quantity != null) {
                currentQuantity = quantity
                binding.quantityText.text = currentQuantity.toString()
            }
        }
    }

    private fun showCartBottomSheet() {
        val bottomSheetFragment = CartBottomSheetFragment()
        bottomSheetFragment.onViewCartClickListener = {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }
        bottomSheetFragment.onContinueShoppingClickListener = {
            finish()
        }
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}

