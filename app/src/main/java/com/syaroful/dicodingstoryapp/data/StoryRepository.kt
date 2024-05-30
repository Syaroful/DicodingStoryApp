package com.syaroful.dicodingstoryapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.syaroful.dicodingstoryapp.data.api.ApiService
import com.syaroful.dicodingstoryapp.data.pref.UserModel
import com.syaroful.dicodingstoryapp.data.pref.UserPreference
import com.syaroful.dicodingstoryapp.data.response.FileUploadResponse
import com.syaroful.dicodingstoryapp.data.response.ListStoryItem
import com.syaroful.dicodingstoryapp.data.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getStories(): LiveData<ResultState<List<ListStoryItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getStories()
            emit(ResultState.Success(successResponse.listStory))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message ?: "Unknown Error"))
        }
    }

    fun getStoriesWithLocation(): LiveData<ResultState<List<ListStoryItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getStoriesWithLocation()
            emit(ResultState.Success(successResponse.listStory))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.toString()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message ?: "Unknown Error"))
        }
    }

    fun uploadImage(imageFile: File, description: String) = liveData {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadImage(multipartBody, requestBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

//    companion object {
//        @Volatile
//        private var instance: StoryRepository? = null
//        fun getInstance(
//            userPreference: UserPreference,
//            apiService: ApiService
//        ): StoryRepository =
//            instance ?: synchronized(this) {
//                instance ?: StoryRepository(userPreference, apiService)
//            }.also { instance = it }
//    }
    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = StoryRepository(userPreference, apiService)
    }
}