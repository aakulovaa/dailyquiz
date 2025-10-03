package com.example.dailyquiz.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dailyquiz.data.viewModels.quiz.QuizState
import com.example.dailyquiz.data.viewModels.quiz.QuizViewModel
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor

@Composable
fun MainScreen(navController: NavController) {
    val viewModel: QuizViewModel = viewModel()
    val quizState by viewModel.quizState

    // Сбрасываем состояние при каждом открытии главного экрана
    LaunchedEffect(Unit) {
        viewModel.resetState()
    }

    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackgroundColor)
            .padding(44.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        when {
            // Основной экран (до нажатия кнопки) - состояние Idle
            quizState is QuizState.Idle -> {
                MainContent(
                    onStartQuiz = {
                        isLoading = true
                        viewModel.loadQuestions()
                    },
                    onHistory = {
                        navController.navigate("history")
                    },
                    viewModel = viewModel
                )
            }

            // Состояние загрузки
            isLoading && quizState is QuizState.Loading -> {
                LoadingState()
            }

            // Состояние ошибки
            isLoading && quizState is QuizState.Error -> {
                ErrorState(
                    errorMessage = (quizState as QuizState.Error).message,
                    onRetry = {
                        isLoading = true
                        viewModel.loadQuestions()
                    },
                    onBack = {
                        isLoading = false
                        viewModel.resetState() // Сбрасываем состояние при возврате
                    }
                )
            }

            // Успешная загрузка - переход к викторине
            quizState is QuizState.Success -> {
                val questions = (quizState as QuizState.Success).questions
                LaunchedEffect(questions) {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "quizQuestions", questions
                    )
                    navController.navigate("quiz")
                    isLoading = false
                    // Не сбрасываем состояние здесь, чтобы сохранить данные для истории
                }
                LoadingState()
            }
        }
    }
}