package com.syaroful.dicodingstoryapp.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.syaroful.dicodingstoryapp.data.ResultState
import com.syaroful.dicodingstoryapp.data.pref.UserModel
import com.syaroful.dicodingstoryapp.databinding.ActivityLoginBinding
import com.syaroful.dicodingstoryapp.view.AuthViewModelFactory
import com.syaroful.dicodingstoryapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {

        binding.loginButton.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.login(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Error -> {
                            showLoading(false)
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }

                        is ResultState.Success -> {
                            showLoading(false)
                            viewModel.saveSession(
                                UserModel(
                                    email,
                                    result.data.loginResult.token.toString(),
                                    true
                                )
                            )
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }

            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}