package com.syaroful.dicodingstoryapp.view.maps

import androidx.lifecycle.ViewModel
import com.syaroful.dicodingstoryapp.data.StoryRepository

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getStoriesWithLocation() = repository.getStoriesWithLocation()
}