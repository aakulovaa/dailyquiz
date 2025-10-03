package com.example.dailyquiz.data.repository

import com.example.dailyquiz.domain.models.QuizAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryRepository {
    companion object {
        private var _instance: HistoryRepository? = null
        fun getInstance(): HistoryRepository {
            return _instance ?: HistoryRepository().also { _instance = it }
        }
    }

    private val _attempts = MutableStateFlow<List<QuizAttempt>>(emptyList())
    val attempts: StateFlow<List<QuizAttempt>> = _attempts.asStateFlow()

    fun saveQuizAttempt(attempt: QuizAttempt) {
        println("DEBUG: Saving attempt with ID: ${attempt.attemptId}")
        println("DEBUG: Current attempts before save: ${_attempts.value.size}")
        _attempts.value = _attempts.value + attempt
        println("DEBUG: Current attempts after save: ${_attempts.value.size}")
    }

    fun deleteAttempt(attemptId: String) {
        _attempts.value = _attempts.value.filter { it.attemptId != attemptId }
    }
}