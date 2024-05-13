package com.syaroful.dicodingstoryapp.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.syaroful.dicodingstoryapp.R
import com.syaroful.dicodingstoryapp.databinding.ActivityOnboardingBinding
import com.syaroful.dicodingstoryapp.view.ViewModelFactory
import com.syaroful.dicodingstoryapp.view.login.LoginActivity
import com.syaroful.dicodingstoryapp.view.main.MainActivity
import com.syaroful.dicodingstoryapp.view.signup.SignupActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val viewModel by viewModels<OnboardingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        setupView()
        setupAction()
    }

    private fun setupView() {
        binding.ivOnboarding.setImageResource(R.drawable.innovation_pana)
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }


}