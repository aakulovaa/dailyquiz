package com.example.dailyquiz.domain.models

data class Quiz(
    val quizId: Int,
    val quizQuestion: String,
    val quizOptions: List<String>,
    val quizCorrectAnswer: String,
    var quizUserAnswer: String? = null
)
