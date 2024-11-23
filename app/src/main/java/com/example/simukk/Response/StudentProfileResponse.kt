package com.example.simukk.Response

import com.example.simukk.Model.Major
import com.example.simukk.Model.User

data class StudentProfileResponse(
    val created_at: String,
    val grade_level: Int,
    val id: Int,
    val major: Major,
    val major_id: Int,
    val nisn: String,
    val updated_at: String,
    val user: User,
    val user_id: Int
)