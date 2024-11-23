package com.example.simukk.Response

import com.example.simukk.Model.User

data class StudentHomeResponse(
    val notCompetentCount: Int,
    val standardsCount: Int,
    val user: User
)