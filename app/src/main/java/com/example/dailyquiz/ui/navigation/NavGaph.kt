package com.example.dailyquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailyquiz.ui.screens.MainScreen
import com.example.dailyquiz.ui.screens.QuizScreen
import com.example.dailyquiz.ui.screens.ResultsScreen

@Composable
fun QuizAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("quiz") { QuizScreen(navController) }
        composable("results") { ResultsScreen(navController) }
    }
}