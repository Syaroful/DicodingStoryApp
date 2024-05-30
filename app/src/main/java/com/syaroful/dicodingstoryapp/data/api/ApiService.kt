package com.syaroful.dicodingstoryapp.data.api

import com.syaroful.dicodingstoryapp.data.response.DetailStoryResponse
import com.syaroful.dicodingstoryapp.data.response.FileUploadResponse
import com.syaroful.dicodingstoryapp.data.response.LoginResponse
import com.syaroful.dicodingstoryapp.data.response.RegisterResponse
import com.syaroful.dicodingstoryapp.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStories(
        @Path("id") id: String
    ): DetailStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
    ): StoryResponse
}