package com.example.androidtest2.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.androidtest2.R
import com.example.androidtest2.databinding.FragmentCartBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CartBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCartBottomSheetBinding
    var onViewCartClickListener: (() -> Unit)? = null
    var onContinueShoppingClickListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize ViewBinding
        binding = FragmentCartBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button actions
        binding.btnViewCart.setOnClickListener {
            onViewCartClickListener?.invoke()
            dismiss()
        }

        binding.btnContinueShopping.setOnClickListener {
            onContinueShoppingClickListener?.invoke()
            dismiss()
        }
    }
}
