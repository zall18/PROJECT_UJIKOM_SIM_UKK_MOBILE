package com.example.simukk.Model

data class User(
    val id: Int,
    val full_name: String,
    val username: String,
    val email: String,
    val email_verified_at: String?, // Nullable since it's null in your data
    val phone: String,
    val role: String,
    val is_active: Int,
    val created_at: String,
    val updated_at: String
)
