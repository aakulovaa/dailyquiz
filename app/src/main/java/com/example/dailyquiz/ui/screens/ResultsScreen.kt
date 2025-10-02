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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.dailyquiz.domain.models.Quiz
import com.example.dailyquiz.domain.models.QuizAttempt
import com.example.dailyquiz.ui.screens.history.HistoryViewModel
import com.example.dailyquiz.ui.screens.history.HistoryViewModelFactory
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor

@Composable
fun ResultsScreen(
    navController: NavController,
    repository: HistoryRepository,
    questions: List<Quiz> = testQuestions
) {
    val correctAnswers = testQuestions.count { it.quizUserAnswer == it.quizCorrectAnswer }
    val totalQuestions = testQuestions.size

    val viewModel: HistoryViewModel = viewModel(
        factory = HistoryViewModelFactory(repository)
    )

    LaunchedEffect(Unit) {
        val attempt = QuizAttempt(
            quizTitle = "Quiz ${repository.attempts.value.size + 1}",
            timestamp = System.currentTimeMillis(),
            correctAnswers = correctAnswers,
            totalQuestions = totalQuestions,
            questions = questions.map { it.copy() }
        )
        viewModel.saveQuizAttempt(attempt)
    }


    val filledStars = when (correctAnswers) {
        totalQuestions -> 5
        totalQuestions - 1 -> 4
        totalQuestions - 2 -> 3
        totalQuestions - 3 -> 2
        totalQuestions - 4 -> 1
        else -> 0
    }

    val resultTextTitle = when (correctAnswers) {
        totalQuestions -> "Идеально!"
        totalQuestions - 1 -> "Почти идеально!"
        totalQuestions - 2 -> "Хороший результат!"
        totalQuestions - 3 -> "Есть над чем поработать."
        totalQuestions - 4 -> "Сложный вопрос?"
        else -> "Бывает и так!"
    }

    val resultTextSubtitle = when (correctAnswers) {
        totalQuestions -> "5/5 - вы ответили на всё правильно. Это блестящий результат!"
        totalQuestions - 1 -> "4/5 - очень близко к совершенству. Ещё один шаг!"
        totalQuestions - 2 -> "3/5 - вы на верном пути. Продолжайте тренироваться!"
        totalQuestions - 3 -> "2/5 - не расстраивайтесь, попробуйте еще раз!"
        totalQuestions - 4 -> "1/5 - иногда просто не ваш день. Следующая попытка будет лучше!"
        else -> "0/5 - не отчаивайтесь. Начните заново и удивите себя!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackgroundColor)
            .padding(horizontal = 24.dp, vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Результаты",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))


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
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            painter = painterResource(id = R.drawable.icon_star),
                            contentDescription = "Звезда рейтинга",
                            tint = if (index < filledStars) Color(0xFFFFB400) else Color(0xFFBABABA),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$correctAnswers из $totalQuestions",
                    color = Color(0xFFFFB400),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = resultTextTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = resultTextSubtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        questions.forEach { it.quizUserAnswer = null }
                        navController.popBackStack("main", inclusive = false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBackgroundColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Начать заново".uppercase(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}