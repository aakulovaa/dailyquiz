package com.example.dailyquiz.data.remote.models

data class QuizApiResponse(
    val response_code: Int,
    val results: List<ApiQuestion>
)