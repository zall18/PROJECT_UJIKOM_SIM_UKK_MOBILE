package com.example.simukk

import com.example.simukk.Request.LoginRequest
import com.example.simukk.Response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    fun loginRoute(@Body loginRequest: LoginRequest): Call<LoginResponse>

}