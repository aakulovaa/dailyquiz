package com.example.dailyquiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import com.example.dailyquiz.R
import com.example.dailyquiz.domain.models.Quiz
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor

// Test data for quiz questions
val testQuestions = listOf(
    Quiz(
        quizId = 1,
        quizQuestion = "Столица Франции?",
        quizOptions = listOf("Лондон", "Берлин", "Париж", "Мадрид"),
        quizCorrectAnswer = "Париж"
    ),
    Quiz(
        quizId = 2,
        quizQuestion = "2 + 2 = ?",
        quizOptions = listOf("3", "4", "5", "6"),
        quizCorrectAnswer = "4"
    ),
    Quiz(
        quizId = 1,
        quizQuestion = "Столица Франции?",
        quizOptions = listOf("Лондон", "Берлин", "Париж", "Мадрид"),
        quizCorrectAnswer = "Париж"
    ),
    Quiz(
        quizId = 2,
        quizQuestion = "2 + 2 = ?",
        quizOptions = listOf("3", "4", "5", "6"),
        quizCorrectAnswer = "4"
    ),
    Quiz(
        quizId = 2,
        quizQuestion = "2 + 2 = ?",
        quizOptions = listOf("3", "4", "5", "6"),
        quizCorrectAnswer = "4"
    )
)

@Composable
fun QuizScreen(navController: NavController) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf("") }
    val currentQuestion = testQuestions[currentQuestionIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackgroundColor)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.White
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Логотип",
                tint = Color.White,
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.size(28.dp))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
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
                Text(
                    text = "Вопрос ${currentQuestionIndex + 1} из ${testQuestions.size}",
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
                    AnswerOption(
                        text = option,
                        isSelected = selectedAnswer == option,
                        onOptionSelected = {
                            selectedAnswer = option
                            currentQuestion.quizUserAnswer = option
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (currentQuestionIndex < testQuestions.size - 1) {
                            currentQuestionIndex++
                            selectedAnswer = ""
                        } else {
                            // Navigate to results
                            navController.navigate("results")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBackgroundColor,
                        contentColor = Color.White
                    ),
                    enabled = selectedAnswer.isNotEmpty()
                ) {
                    Text(
                        text = if (currentQuestionIndex < testQuestions.size - 1) "Далее".uppercase() else "Завершить".uppercase(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Вернуться к предыдущим вопросам невозможно",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}