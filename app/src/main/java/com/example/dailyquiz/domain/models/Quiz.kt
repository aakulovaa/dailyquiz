package com.example.dailyquiz.domain.models

import kotlinx.serialization.Serializable

data class Quiz(
    val quizId: Int,
    val quizQuestion: String,
    val quizOptions: List<String>,
    val quizCorrectAnswer: String,
    var quizUserAnswer: String = ""
)
