package com.example.simukk.Response

import com.example.simukk.Model.User

data class LoginResponse(
    val token: String,
    val token_type: String,
    val role: String,
    val user: User,
    val message: String
)
