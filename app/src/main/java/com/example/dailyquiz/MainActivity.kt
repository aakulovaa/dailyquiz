package com.example.dailyquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dailyquiz.ui.navigation.QuizAppNavigation
import com.example.dailyquiz.ui.theme.DailyquizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyquizTheme {
                QuizAppNavigation()
            }
        }
    }
}


