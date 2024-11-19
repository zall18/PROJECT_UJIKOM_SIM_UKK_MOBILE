package com.example.simukk.Model

data class Student(
    val id: Int,
    val nisn: String,
    val grade_level: Int,
    val major_id: Int,
    val user_id: Int,
    val created_at: String,
    val updated_at: String,
    val user: User
)
