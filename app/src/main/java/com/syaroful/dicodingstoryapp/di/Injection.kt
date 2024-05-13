package com.syaroful.dicodingstoryapp.di

import android.content.Context
import com.syaroful.dicodingstoryapp.data.StoryRepository
import com.syaroful.dicodingstoryapp.data.UserRepository
import com.syaroful.dicodingstoryapp.data.api.ApiConfig
import com.syaroful.dicodingstoryapp.data.pref.UserPreference
import com.syaroful.dicodingstoryapp.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideStoryRepository(context: Context): StoryRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(preference, apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(preference, apiService)
    }
}