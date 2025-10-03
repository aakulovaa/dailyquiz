package com.example.dailyquiz.data.viewModels.quiz

import android.text.Html
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiz.data.remote.api.RetrofitInstance
import com.example.dailyquiz.domain.models.Quiz
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val _quizState = mutableStateOf<QuizState>(QuizState.Idle)
    val quizState: State<QuizState> = _quizState

    fun loadQuestions() {
        viewModelScope.launch {
            _quizState.value = QuizState.Loading
            try {
                val response = RetrofitInstance.api.getQuestions()
                if (response.response_code == 0 && response.results.isNotEmpty()) {
                    val quizList = response.results.mapIndexed { index, apiQuestion ->
                        Quiz(
                            quizId = index + 1,
                            quizQuestion = Html.fromHtml(apiQuestion.question, Html.FROM_HTML_MODE_LEGACY).toString(),
                            quizOptions = (apiQuestion.incorrect_answers + apiQuestion.correct_answer).shuffled(),
                            quizCorrectAnswer = apiQuestion.correct_answer
                        )
                    }
                    _quizState.value = QuizState.Success(quizList)
                } else {
                    _quizState.value = QuizState.Error("Не удалось загрузить вопросы")
                }
            } catch (e: Exception) {
                _quizState.value = QuizState.Error("Ошибка сети: ${e.message}")
            }
        }
    }

    // Функция для полного сброса состояния
    fun resetState() {
        _quizState.value = QuizState.Idle
    }
}