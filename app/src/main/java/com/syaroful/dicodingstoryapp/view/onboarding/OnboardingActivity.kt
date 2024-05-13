package com.syaroful.dicodingstoryapp.view.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.syaroful.dicodingstoryapp.R
import com.syaroful.dicodingstoryapp.databinding.ActivityOnboardingBinding
import com.syaroful.dicodingstoryapp.view.ViewModelFactory
import com.syaroful.dicodingstoryapp.view.login.LoginActivity
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

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        val imageAnimation =
            ObjectAnimator.ofFloat(binding.ivOnboarding, View.ALPHA, 1f).setDuration(200)
        val textAnimation =
            ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(200)
        val textDescAnimation =
            ObjectAnimator.ofFloat(binding.tvDescription, View.ALPHA, 1f).setDuration(200)
        val btnLoginAnimation =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(200)
        val btnSignupAnimation =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                imageAnimation,
                textAnimation,
                textDescAnimation,
                btnLoginAnimation,
                btnSignupAnimation
            )
            start()
        }

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