package com.example.retrofitpractice

import com.example.retrofitpractice.models.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts/1")
    suspend fun getPosts():Response<User>
}