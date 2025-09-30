package com.example.dailyquiz.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.dailyquiz.domain.models.QuizAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryRepository {
    private val _attempts = MutableStateFlow<List<QuizAttempt>>(emptyList())
    val attempts: StateFlow<List<QuizAttempt>> = _attempts.asStateFlow()

    fun addAttempt(attempt: QuizAttempt) {
        _attempts.value = listOf(attempt) + _attempts.value
    }

    fun saveQuizAttempt(attempt: QuizAttempt) {
        _attempts.value = _attempts.value + attempt
    }

    fun deleteAttempt(attemptId: String) {
        _attempts.value = _attempts.value.filterNot { it.attemptId == attemptId }
    }
}