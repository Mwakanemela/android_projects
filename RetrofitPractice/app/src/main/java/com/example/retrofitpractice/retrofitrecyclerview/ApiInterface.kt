package com.example.retrofitpractice.retrofitrecyclerview

import com.example.retrofitpractice.retrofitrecyclerview.models.Users
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/posts")
    suspend fun getAllUsers(): Response<Users>
}