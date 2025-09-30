package com.example.dailyquiz.ui.screens.history

import androidx.lifecycle.ViewModel
import com.example.dailyquiz.data.repository.HistoryRepository
import com.example.dailyquiz.domain.models.QuizAttempt
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    val historyState: StateFlow<List<QuizAttempt>> = repository.attempts

    fun saveQuizAttempt(attempt: QuizAttempt) {
        repository.saveQuizAttempt(attempt)
    }

    fun deleteAttempt(attemptId: String) {
        repository.deleteAttempt(attemptId)
    }
}

