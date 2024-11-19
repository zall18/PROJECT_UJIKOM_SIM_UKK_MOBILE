package com.example.simukk

import com.example.simukk.Request.LoginRequest
import com.example.simukk.Response.AssessorHomeResponse
import com.example.simukk.Response.ComeptencyStandardResponse
import com.example.simukk.Response.CompetencyStudentResponse
import com.example.simukk.Response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    fun loginRoute(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("assessor/home")
    fun assessorHome(@Header("Authorization") token: String): Call<AssessorHomeResponse>

    @GET("assessor/competency")
    fun competencyStandard(@Header("Authorization") token: String): Call<ComeptencyStandardResponse>

    @GET("assessor/competency/{id}/student")
    fun competencyStudent(@Header("Authorization") token: String, @Path("id") id: String): Call<CompetencyStudentResponse>

}