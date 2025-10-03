package com.example.dailyquiz.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.dailyquiz.domain.models.QuizAttempt
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor

@Composable
fun HistoryScreen(
    navController: NavController,
    repository: HistoryRepository = remember { HistoryRepository() }
) {
    val viewModel: HistoryViewModel = viewModel(
        factory = HistoryViewModelFactory(repository)
    )
    val attempts by viewModel.historyState.collectAsState()
    var selectedAttempt by remember { mutableStateOf<QuizAttempt?>(null) }
    var showDeleteNotification by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 80.dp)
        ) {
            Text(
                text = "История",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(52.dp))

            if (attempts.isEmpty()) {
                EmptyHistoryState(navController)
            } else {
                HistoryList(
                    attempts = attempts,
                    onDeleteAttempt = { attemptId ->
                        viewModel.deleteAttempt(attemptId)
                        showDeleteNotification = true
                        selectedAttempt = null
                    },
                    onItemClick = { attempt ->
                        // Навигация происходит здесь
                        navController.navigate("review/${attempt.attemptId}")
                    },
                    selectedAttempt = selectedAttempt,
                    onSelectedAttemptChange = { selectedAttempt = it }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Логотип",
                tint = Color.White,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Затемнение всего экрана
        if (selectedAttempt != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { selectedAttempt = null }
            )
        }

        // Кнопка удаления поверх затемнения
        if (selectedAttempt != null) {
            Box(
                modifier = Modifier
                    .offset(x = 190.dp, y = 126.dp)
                    .width(170.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clickable {
                        viewModel.deleteAttempt(selectedAttempt?.attemptId ?: "")
                        showDeleteNotification = true
                        selectedAttempt = null
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Удалить",
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(
                        text = "Удалить",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Диалог подтверждения удаления
        if (showDeleteNotification) {
            DeleteNotificationDialog(
                onDismiss = { showDeleteNotification = false }
            )
        }
    }
}