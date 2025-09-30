package com.example.dailyquiz.domain.models

import java.util.UUID

data class QuizAttempt(
    val attemptId: String = UUID.randomUUID().toString(),
    val quizTitle: String,
    val timestamp: Long = System.currentTimeMillis(),
    val correctAnswers: Int,
    val totalQuestions: Int,
    val questions: List<Quiz>
)
