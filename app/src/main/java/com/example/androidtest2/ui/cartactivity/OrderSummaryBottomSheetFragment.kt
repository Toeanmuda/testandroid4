package com.example.androidtest2.ui.cartactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.androidtest2.R
import com.example.androidtest2.databinding.FragmentOrderSummaryBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.viewModels

class OrderSummaryBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentOrderSummaryBottomSheetBinding
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderSummaryBottomSheetBinding.inflate(inflater, container, false)

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            var orderSummary = ""
            var totalPrice = 0.0

            for (cartItem in cartItems) {
                orderSummary += "${cartItem.product.title} (Qty: ${cartItem.quantity}) - $${cartItem.product.price * cartItem.quantity}\n"
                totalPrice += cartItem.product.price * cartItem.quantity
            }

            binding.orderSummaryText.text = orderSummary
            binding.totalPriceText.text = "Total: $${"%.2f".format(totalPrice)}"
        }

        binding.confirmCheckoutButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}
