package com.example.simukk.Response

import com.example.simukk.Model.User

data class AssessorHomeResponse(
    val user: User,
    val competency_count: Int
)
