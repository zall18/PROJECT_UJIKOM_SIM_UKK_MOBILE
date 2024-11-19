package com.example.simukk.Response

import com.example.simukk.Model.User

data class AssessorHomeResponse(
    val user: User,
    val competency_count: Int,
    val competitor_count: Int,
    val avg_last_score: Int,
    val student_active: Int
)
