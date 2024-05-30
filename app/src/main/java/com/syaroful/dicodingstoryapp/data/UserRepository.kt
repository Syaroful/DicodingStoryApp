package com.syaroful.dicodingstoryapp.data

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.syaroful.dicodingstoryapp.data.api.ApiService
import com.syaroful.dicodingstoryapp.data.pref.UserModel
import com.syaroful.dicodingstoryapp.data.pref.UserPreference
import com.syaroful.dicodingstoryapp.data.response.LoginResponse
import com.syaroful.dicodingstoryapp.data.response.RegisterResponse
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun login(name: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(name, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}