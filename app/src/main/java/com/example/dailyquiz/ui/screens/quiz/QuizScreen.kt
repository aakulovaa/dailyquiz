package com.example.dailyquiz.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dailyquiz.R
import com.example.dailyquiz.data.repository.HistoryRepository
import com.example.dailyquiz.data.viewModels.history.HistoryViewModel
import com.example.dailyquiz.data.viewModels.history.HistoryViewModelFactory
import com.example.dailyquiz.domain.models.Quiz
import com.example.dailyquiz.domain.models.QuizAttempt
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(navController: NavController, repository: HistoryRepository) {
    val questions = navController.previousBackStackEntry?.savedStateHandle?.get<List<Quiz>>("quizQuestions")
        ?: emptyList()

    val scrollState = rememberScrollState()

    if (questions.isEmpty()) {
        // Обработка случая, когда вопросы не переданы
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBackgroundColor)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Вопросы не загружены", color = Color.White)
            Button(onClick = { navController.popBackStack() }) {
                Text("Назад")
            }
        }
        return
    }

    val historyViewModel: HistoryViewModel = viewModel(
        factory = HistoryViewModelFactory(repository)
    )

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf("") }
    val currentQuestion = questions[currentQuestionIndex]

    // Состояние для подсветки ответов
    var showAnswerFeedback by remember { mutableStateOf(false) }
    var isTransitioning by remember { mutableStateOf(false) }

    // Таймер на 5 минут (300 секунд)
    var remainingTime by remember { mutableIntStateOf(0) }
    var showTimeOutDialog by remember { mutableStateOf(false) }

    // Получаем выбранные категорию и сложность из предыдущего экрана
    val selectedCategory = navController.previousBackStackEntry?.savedStateHandle?.get<String>("selectedCategory") ?: "Общие знания"
    val selectedDifficulty = navController.previousBackStackEntry?.savedStateHandle?.get<String>("selectedDifficulty") ?: "Легкая"

    // Запускаем таймер
    LaunchedEffect(Unit) {
        while (remainingTime < 300) {
            delay(1000L)
            remainingTime++
        }
        // Когда время вышло
        showTimeOutDialog = true
        // Помечаем все неотвеченные вопросы как неправильные
        questions.forEach { question ->
            if (question.quizUserAnswer.isEmpty()) {
                question.quizUserAnswer = "TIMEOUT"
            }
        }

        // Сохраняем попытку в историю
        val correctAnswers = questions.count {
            it.quizUserAnswer != "TIMEOUT" && it.quizUserAnswer == it.quizCorrectAnswer
        }
        val totalQuestions = questions.size

        val attempt = QuizAttempt(
            quizTitle = "Quiz ⏰❌",
            timestamp = System.currentTimeMillis(),
            correctAnswers = correctAnswers,
            totalQuestions = totalQuestions,
            questions = questions.map { it.copy() },
            timeOut = true,
            category = selectedCategory,
            difficulty = selectedDifficulty
        )
        historyViewModel.saveQuizAttempt(attempt)
    }

    // Обработка подсветки и автоматического перехода
    LaunchedEffect(showAnswerFeedback) {
        if (showAnswerFeedback) {
            // Ждем 2 секунды
            delay(2000L)

            // Переходим к следующему вопросу или завершаем
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                selectedAnswer = ""
            } else {
                navController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("quizQuestions", questions)
                    set("selectedCategory", selectedCategory)
                    set("selectedDifficulty", selectedDifficulty)
                }
                navController.navigate("results")
            }

            // Сбрасываем состояния
            showAnswerFeedback = false
            isTransitioning = false
        }
    }

    // Затемнение экрана при показе диалога
    if (showTimeOutDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )
    }

    // Блокируем интерфейс во время подсветки
    val uiEnabled = !showAnswerFeedback && !isTransitioning

    Column(
    modifier = Modifier
        .fillMaxSize()
        .background(PrimaryBackgroundColor)
        .padding(24.dp)
        .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { if (uiEnabled) {
                    navController.popBackStack()
                    }
                },
                enabled = uiEnabled
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = if (uiEnabled) Color.White else Color.Gray
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Логотип",
                tint = Color.White,
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.size(10.dp))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y=(-55).dp)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Таймер - полоса прогресса
                TimerProgressBar(
                    remainingTime = remainingTime,
                    totalTime = 300,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .offset(y = (-10).dp)
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Вопрос ${currentQuestionIndex + 1} из ${questions.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFCEC9FF),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentQuestion.quizQuestion,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Answer options
                currentQuestion.quizOptions.forEach { option ->
                    val isCorrect = option == currentQuestion.quizCorrectAnswer
                    val isSelected = selectedAnswer == option
                    val showAsIncorrect = showAnswerFeedback && isSelected && !isCorrect
                    val showAsCorrect = showAnswerFeedback && isCorrect

                    AnswerOptionWithHighlight(
                        text = option,
                        isSelected = isSelected,
                        isCorrect = showAsCorrect,
                        isIncorrect = showAsIncorrect,
                        onOptionSelected = {
                            if (uiEnabled) {
                                selectedAnswer = option
                                currentQuestion.quizUserAnswer = option
                            }
                        },
                        enabled = uiEnabled
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (uiEnabled && selectedAnswer.isNotEmpty()) {
                            isTransitioning = true
                            showAnswerFeedback = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiEnabled) PrimaryBackgroundColor else Color.Gray,
                        contentColor = Color.White
                    ),
                    enabled = selectedAnswer.isNotEmpty() && uiEnabled
                ) {
                    Text(
                        text = if (currentQuestionIndex < questions.size - 1) "Далее".uppercase() else "Завершить".uppercase(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }



        Text(
            text = "Вернуться к предыдущим вопросам невозможно",
            style = MaterialTheme.typography.bodySmall,
            color = if (uiEnabled) Color.White else Color.Gray,
            modifier = Modifier.fillMaxWidth().offset(y=(-55).dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))
    }

    // Диалог истечения времени
    if (showTimeOutDialog) {
        TimeOutNotificationDialog(
            onDismiss = {
                navController.navigate("main")
            }
        )
    }
}