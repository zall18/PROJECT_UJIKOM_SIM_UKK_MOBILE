package com.example.simukk.Response

import com.example.simukk.Model.StatusSummary

data class StudentExamResultResponse(
    val statusSummary: MutableList<StatusSummary>
)