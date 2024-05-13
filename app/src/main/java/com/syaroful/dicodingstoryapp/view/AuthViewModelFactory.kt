package com.syaroful.dicodingstoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syaroful.dicodingstoryapp.data.UserRepository
import com.syaroful.dicodingstoryapp.di.Injection
import com.syaroful.dicodingstoryapp.view.login.LoginViewModel
import com.syaroful.dicodingstoryapp.view.signup.SignupViewModel

class AuthViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context) =
            AuthViewModelFactory(Injection.provideUserRepository(context))

    }
}