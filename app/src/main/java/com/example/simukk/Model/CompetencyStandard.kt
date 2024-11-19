package com.example.simukk.Model

data class CompetencyStandard(
    val id: Int,
    val unit_code: String,
    val unit_title: String,
    val unit_description: String,
    val major_id: Int,
    val assessor_id: Int,
    val created_at: String,
    val updated_at: String
)
