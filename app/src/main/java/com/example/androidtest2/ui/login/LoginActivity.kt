package com.example.androidtest2.ui.login

import com.example.androidtest2.ui.main.MainActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest2.utils.Utils
import com.example.androidtest2.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Utils.getToken(this).isNullOrEmpty()) {
            gotoMain()
        }

        loginViewModel.loginResult.observe(this) { result ->
            if (result.token != null)
                if (result.token.isNotEmpty()) {
                    Utils.saveToken(this, result.token.toString())
                    gotoMain()
                }
        }

        loginViewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            loginViewModel.login(username, password)
        }
    }

    private fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
