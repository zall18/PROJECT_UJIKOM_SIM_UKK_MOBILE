package com.example.simukk.Response

import com.example.simukk.Model.ExamDetail

data class ExamResultResponse(
    val unit_title: String,
    val exam_result: Map<String, ExamDetail>
)



