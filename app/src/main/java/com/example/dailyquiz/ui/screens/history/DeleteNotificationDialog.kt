package com.example.dailyquiz.ui.screens.history

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteNotificationDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Попытка удалена")
        },
        text = {
            Text("Вы можете пройти викторину снова, когда будете готовы.")
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Закрыть")
            }
        }
    )
}