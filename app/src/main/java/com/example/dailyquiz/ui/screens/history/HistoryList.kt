package com.example.dailyquiz.ui.screens.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyquiz.domain.models.QuizAttempt

@Composable
fun HistoryList(
    attempts: List<QuizAttempt>,
    onDeleteAttempt: (String) -> Unit,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAttempt by remember { mutableStateOf<QuizAttempt?>(null) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Удаление попытки") },
            text = { Text("Вы уверены, что хотите удалить эту попытку?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedAttempt?.attemptId?.let(onDeleteAttempt)
                        showDialog = false
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    LazyColumn {
        items(attempts, key = { it.attemptId }) { attempt ->
            HistoryItem(
                attempt = attempt,
                onItemClick = {
                    // Переход к разбору викторины
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "reviewQuiz",
                        attempt
                    )
                    navController.navigate("review")
                },
                onItemLongClick = {
                    selectedAttempt = attempt
                    showDialog = true
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}