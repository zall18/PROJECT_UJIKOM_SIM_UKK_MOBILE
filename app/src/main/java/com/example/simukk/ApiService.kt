package com.example.simukk

import com.example.simukk.Model.User
import com.example.simukk.Request.GradingRequest
import com.example.simukk.Request.LoginRequest
import com.example.simukk.Response.AssessorHomeResponse
import com.example.simukk.Response.ComeptencyStandardResponse
import com.example.simukk.Response.CompetencyStudentResponse
import com.example.simukk.Response.ElementStatusResponse
import com.example.simukk.Response.ExamResultResponse
import com.example.simukk.Response.GradingResponse
import com.example.simukk.Response.LoginResponse
import com.example.simukk.Response.LogoutResponse
import com.example.simukk.Response.StandardElementResponse
import com.example.simukk.Response.StudentExamResultResponse
import com.example.simukk.Response.StudentHomeResponse
import com.example.simukk.Response.StudentProfileResponse
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

    @GET("assessor/competency/{standard_id}/student/{id}")
    fun elementStatus(@Header("Authorization") token: String, @Path("standard_id") standard_id: String, @Path("id") id: String): Call<ElementStatusResponse>

    @POST("assessor/competency/element/{element_id}/student/{student_id}")
    fun grading(@Body gradingRequest: GradingRequest, @Header("Authorization") token: String, @Path("element_id") element_id: String, @Path("student_id") student_id: String): Call<GradingResponse>

    @GET("assessor/competency-standard/{id}")
    fun StandardElement(@Header("Authorization") token: String, @Path("id") id: String): Call<StandardElementResponse>

    @GET("assessor/exam-result/{id}")
    fun ExamResult(@Header("Authorization") token: String, @Path("id") id: String): Call<ExamResultResponse>

    @GET("student/home")
    fun studentHome(@Header("Authorization") token: String): Call<StudentHomeResponse>

    @GET("me")
    fun me(@Header("Authorization") token: String): Call<User>

    @GET("student/exam-result")
    fun studentExamResult(@Header("Authorization") token: String): Call<StudentExamResultResponse>

    @GET("student/profile")
    fun studentProfile(@Header("Authorization") token: String): Call<StudentProfileResponse>

    @GET("auth/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>
}