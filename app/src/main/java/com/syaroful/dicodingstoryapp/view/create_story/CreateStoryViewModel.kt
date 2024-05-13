package com.syaroful.dicodingstoryapp.view.create_story

import androidx.lifecycle.ViewModel
import com.syaroful.dicodingstoryapp.data.StoryRepository
import java.io.File

class CreateStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
}