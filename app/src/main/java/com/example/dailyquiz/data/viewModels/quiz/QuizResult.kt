package com.example.dailyquiz.data.viewModels.quiz

import com.example.dailyquiz.domain.models.Quiz

data class QuizResult(
    val correctAnswers: Int,
    val totalQuestions: Int,
    val questions: List<Quiz>
)
