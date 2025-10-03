package com.example.dailyquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailyquiz.data.repository.HistoryRepository
import com.example.dailyquiz.ui.screens.quiz.QuizScreen
import com.example.dailyquiz.ui.screens.ResultsScreen
import com.example.dailyquiz.ui.screens.history.HistoryScreen
import com.example.dailyquiz.ui.screens.main.MainScreen
import com.example.dailyquiz.ui.screens.resultPreview.ReviewResultScreen

@Composable
fun QuizAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        val repository = HistoryRepository()

        composable("main") { MainScreen(navController) }
        composable("quiz") { QuizScreen(navController, repository) }

        composable("results") { ResultsScreen(navController, repository) }
        composable("history") { HistoryScreen(navController, repository) }

        composable("review/{attemptId}") { backStackEntry ->
            val attemptId = backStackEntry.arguments?.getString("attemptId")
            ReviewResultScreen(
                navController = navController,
                repository,
                attemptId = attemptId
            )
        }

    }
}