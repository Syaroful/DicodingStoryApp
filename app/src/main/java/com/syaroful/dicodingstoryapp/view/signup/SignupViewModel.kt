package com.syaroful.dicodingstoryapp.view.signup

import androidx.lifecycle.ViewModel
import com.syaroful.dicodingstoryapp.data.UserRepository

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, pass: String) = repository.register(name, email, pass)
}