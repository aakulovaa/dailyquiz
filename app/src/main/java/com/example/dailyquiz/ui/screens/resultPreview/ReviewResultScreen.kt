package com.example.dailyquiz.ui.screens.resultPreview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.dailyquiz.ui.theme.BorderColor
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor

@Composable
fun ReviewResultScreen(
    navController: NavController,
    repository: HistoryRepository = remember { HistoryRepository() },
    attemptId: String?
) {
    val viewModel: HistoryViewModel = viewModel(
        factory = HistoryViewModelFactory(repository)
    )

    val attempt = viewModel.getAttemptById(attemptId)

    // Если попытка не найдена
    val safeAttempt = attempt ?: run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBackgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Результат не найден",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text("Назад")
            }
        }
        return
    }

    val correctAnswers = safeAttempt.correctAnswers
    val totalQuestions = safeAttempt.totalQuestions
    val questions = safeAttempt.questions

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
            .verticalScroll(rememberScrollState())
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
            shape = RoundedCornerShape(32.dp),
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
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleLarge
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

                // Кнопка "Начать заново"
                Button(
                    onClick = {
                        // Очищаем данные викторины и полностью возвращаемся на главный экран
                        navController.previousBackStackEntry?.savedStateHandle?.remove<List<Quiz>>("quizQuestions")
                        // Полностью очищаем back stack до главного экрана
                        navController.popBackStack("quiz", inclusive = false)

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
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Список вопросов с результатами
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            questions.forEachIndexed { index, question ->
                val isCorrect = question.quizUserAnswer == question.quizCorrectAnswer

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(32.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        // Заголовок вопроса с номером и иконкой
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Вопрос ${index + 1} из 5",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFBABABA),
                                modifier = Modifier.weight(1f)
                            )

                            // Иконка правильности ответа
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = if (isCorrect) Color(0xFF00B11E) else Color(0xFFFD0000),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isCorrect) Icons.Filled.Check else Icons.Filled.Close,
                                    contentDescription = if (isCorrect) "Верно" else "Неверно",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Текст вопроса
                        Text(
                            text = question.quizQuestion,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Варианты ответов
                        question.quizOptions.forEach { option ->
                            val isUserAnswer = option == question.quizUserAnswer
                            val isCorrectAnswer = option == question.quizCorrectAnswer

                            val backgroundColor = Color(0xFFF3F3F3)

                            val textColor = when {
                                isCorrectAnswer -> Color(0xFF00B11E)
                                isUserAnswer && !isCorrectAnswer -> Color(0xFFFD0000)
                                else -> Color.Black
                            }

                            val borderColor = when {
                                isCorrectAnswer -> Color(0xFF00B11E)
                                isUserAnswer && !isCorrectAnswer -> Color(0xFFFD0000)
                                else -> Color.Transparent
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundColor, RoundedCornerShape(12.dp))
                                    .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Круглый пункт с иконкой
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                color = when {
                                                    isCorrectAnswer -> Color(0xFF00B11E)
                                                    isUserAnswer && !isCorrectAnswer -> Color(0xFFFD0000)
                                                    else -> Color(0xFFD1D1D1)
                                                },
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isCorrectAnswer || (isUserAnswer && !isCorrectAnswer)) {
                                            Icon(
                                                imageVector = if (isCorrectAnswer) Icons.Filled.Check else Icons.Filled.Close,
                                                contentDescription = if (isCorrectAnswer) "Верно" else "Неверно",
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = option,
                                        color = textColor,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isUserAnswer || isCorrectAnswer) FontWeight.SemiBold else FontWeight.Normal,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Начать заново"
        Button(
            onClick = {
                navController.navigate("main")
            },
            modifier = Modifier
                .width(280.dp)
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = BorderColor
            )
        ) {
            Text(
                text = "Начать заново".uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}