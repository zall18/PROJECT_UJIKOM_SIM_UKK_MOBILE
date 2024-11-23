package com.example.simukk.Response

import com.example.simukk.Model.ElementStatus

data class ElementStatusResponse(
    val student_id: String,
    val elements: MutableList<ElementStatus>,
    val final_score: Int,
    val status: String
)
