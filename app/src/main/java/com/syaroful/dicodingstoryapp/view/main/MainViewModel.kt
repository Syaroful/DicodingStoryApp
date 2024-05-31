package com.syaroful.dicodingstoryapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syaroful.dicodingstoryapp.data.StoryRepository
import com.syaroful.dicodingstoryapp.data.pref.UserModel
import com.syaroful.dicodingstoryapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    fun getStories() = repository.getStories()

    val story: LiveData<PagingData<ListStoryItem>> =
        repository.getStories().cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}