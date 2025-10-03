package com.example.dailyquiz.data.viewModels.quiz

import com.example.dailyquiz.domain.models.Quiz

sealed class QuizState {
    object Idle : QuizState()          // Исходное состояние (до нажатия кнопки)
    object Loading : QuizState()       // Загрузка
    data class Success(val questions: List<Quiz>) : QuizState()  // Успех
    data class Error(val message: String) : QuizState()          // Ошибка
}