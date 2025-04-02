package com.example.androidtest2.ui.main

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidtest2.R
import com.example.androidtest2.databinding.ActivityMainBinding
import com.example.androidtest2.ui.cartactivity.CartActivity
import com.example.androidtest2.ui.detailproduct.ProductDetailActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var progressDialog: ProgressDialog // Declare ProgressDialog
    var total:Int =0
    var badgeTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        productViewModel.updateCartItemCount()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewProducts.layoutManager = gridLayoutManager
        productAdapter = ProductAdapter(emptyList()) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product) // Pass product object
            startActivity(intent)
        }
        binding.recyclerViewProducts.adapter = productAdapter

        binding.recyclerViewCategories.layoutManager = GridLayoutManager(this, 4)
        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            filterProductsByCategory(category)
        }
        binding.recyclerViewCategories.adapter = categoryAdapter

        productViewModel.products.observe(this, Observer { products ->
            progressDialog.dismiss()
            productAdapter.updateProductList(products)
        })

        productViewModel.categories.observe(this, Observer { categories ->
            categoryAdapter.updateCategoryList(categories)
        })

        productViewModel.errorMessage.observe(this, Observer { errorMessage ->
            progressDialog.dismiss()  // Dismiss the loading dialog in case of error
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()

            productAdapter.updateProductList(emptyList())
        })

        // Observe cart updates to update the badge count
        productViewModel.cartItemCount.observe(this, Observer { count ->
            Log.d("DARMA", "=" + count)
            total = count
            updateCartBadge(count)
        })

        // Load products and categories from ViewModel
        productViewModel.getProducts()
        productViewModel.getCategories()

        progressDialog.show()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val cartMenuItem = menu?.findItem(R.id.action_cart)
        val badgeView = LayoutInflater.from(this).inflate(R.layout.badge_layout, null)

        badgeTextView = badgeView.findViewById(R.id.cart_badge)
        badgeTextView?.text = "$total"
        cartMenuItem?.actionView = badgeView
        badgeView.setOnClickListener {
            openCart()
        }

        return true
    }

    private fun updateCartBadge(count: Int) {
        if (badgeTextView != null)
            badgeTextView!!.text = "" + count
    }


    // Handle click events for menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                openProfile()
                true
            }
            R.id.action_cart -> {
                openCart()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openProfile() {
        val bottomSheetFragment = ProfileBottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun openCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun filterProductsByCategory(category: String) {
        productViewModel.getProductsByCategory(category)
    }
}
