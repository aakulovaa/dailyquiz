package com.example.dailyquiz.ui.screens.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dailyquiz.data.repository.HistoryRepository
import com.example.dailyquiz.domain.models.QuizAttempt
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    val historyState: StateFlow<List<QuizAttempt>> = repository.attempts

    fun getAttemptById(attemptId: String?): QuizAttempt? {
        if (attemptId == null) return null

        val attempts = historyState.value
        println("DEBUG: Searching for attemptId: $attemptId")
        println("DEBUG: Available attempts: ${attempts.map { it.attemptId }}")

        return attempts.find { it.attemptId == attemptId }
    }

    fun saveQuizAttempt(attempt: QuizAttempt) {
        println("DEBUG: Saving attempt with ID: ${attempt.attemptId}")
        repository.saveQuizAttempt(attempt)
    }

    fun deleteAttempt(attemptId: String) {
        repository.deleteAttempt(attemptId)
    }

    private val _selectedAttempt = mutableStateOf<QuizAttempt?>(null)
    val selectedAttempt: QuizAttempt? get() = _selectedAttempt.value

    fun setSelectedAttempt(attempt: QuizAttempt) {
        _selectedAttempt.value = attempt
    }

    fun clearSelectedAttempt() {
        _selectedAttempt.value = null
    }
}