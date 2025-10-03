package com.example.dailyquiz.data.viewModels.quiz

import android.text.Html
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiz.data.remote.api.RetrofitInstance
import com.example.dailyquiz.domain.models.Quiz
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val _quizState = mutableStateOf<QuizState>(QuizState.Idle)
    val quizState: State<QuizState> = _quizState

    // Состояния фильтров
    var selectedCategory by mutableStateOf(9) // Общие знания по умолчанию
        private set

    var selectedDifficulty by mutableStateOf("easy") // Легкая сложность по умолчанию
        private set

    // Доступные категории
    val categories = listOf(
        9 to "Общие знания",
        10 to "Книги",
        11 to "Фильмы",
        12 to "Музыка",
        13 to "Мюзиклы и театр",
        14 to "Телевидение",
        15 to "Видеоигры",
        16 to "Настольные игры",
        17 to "Наука и природа",
        18 to "Компьютеры",
        19 to "Математика",
        20 to "Мифология",
        21 to "Спорт",
        22 to "География",
        23 to "История",
        24 to "Политика",
        25 to "Искусство",
        26 to "Знаменитости",
        27 to "Животные",
        28 to "Транспорт",
        29 to "Комиксы",
        30 to "Гаджеты",
        31 to "Аниме и манга",
        32 to "Мультфильмы"
    )

    // Доступные уровни сложности
    val difficulties = listOf(
        "easy" to "Легкая",
        "medium" to "Средняя",
        "hard" to "Сложная"
    )

    fun updateCategory(categoryId: Int) {
        selectedCategory = categoryId
    }

    fun updateDifficulty(difficulty: String) {
        selectedDifficulty = difficulty
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _quizState.value = QuizState.Loading
            try {
                val response = RetrofitInstance.api.getQuestions(
                    amount = 5,
                    category = selectedCategory,
                    difficulty = selectedDifficulty,
                    type = "multiple"
                )
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