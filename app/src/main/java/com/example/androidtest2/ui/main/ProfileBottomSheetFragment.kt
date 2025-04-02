package com.example.androidtest2.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.androidtest2.utils.Utils
import com.example.androidtest2.databinding.FragmentProfileBottomSheetBinding
import com.example.androidtest2.ui.login.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileBottomSheetBinding? = null
    private val binding get() = _binding!!

    // Menghubungkan dengan ViewModel dari com.example.androidtest2.ui.main.MainActivity
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decodedJWT = decodeJwtToken(Utils.getToken(requireContext()).toString())
        productViewModel.getUser(decodedJWT.subject)

        productViewModel.user.observe(this) { it ->
            run {
                binding.profileName.text = "" + it.get("username")
                binding.profileEmail.text = "Email: " + it.get("email")
            }
        }
        // Handle Logout button click
        binding.logoutButton.setOnClickListener {
            // Handle logout action (clear session data or navigate back to login screen)
            logout()
        }
    }

    private fun logout() {
        dismiss() // Dismiss the BottomSheet
        Utils.saveToken(requireContext(),"")

        // Start LoginActivity
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        // Finish the current Activity (com.example.androidtest2.ui.main.MainActivity in this case)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun decodeJwtToken(token: String): DecodedJWT {
        // Dekode token
        val decodedJWT = JWT.decode(token)
        return decodedJWT
    }
}

