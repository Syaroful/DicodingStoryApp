package com.syaroful.dicodingstoryapp.view.signup

import androidx.lifecycle.ViewModel
import com.syaroful.dicodingstoryapp.data.StoryRepository

class SignupViewModel(private val repository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, pass: String) = repository.register(name, email, pass)
}