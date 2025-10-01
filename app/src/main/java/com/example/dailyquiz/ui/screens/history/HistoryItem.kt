package com.example.dailyquiz.ui.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyquiz.R
import com.example.dailyquiz.domain.models.QuizAttempt
import com.example.dailyquiz.ui.theme.BorderColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryItem(
    attempt: QuizAttempt,
    onItemClick: () -> Unit,
    onItemLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filledStars = attempt.correctAnswers

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onItemClick() },
                    onLongPress = {
                        onItemLongClick()
                    }
                )
            },
        shape = RoundedCornerShape(35.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.padding(24.dp).fillMaxWidth()
            .height(60.dp)) {
            // Заголовок викторины
            Text(
                text = attempt.quizTitle,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = BorderColor,
                modifier = Modifier.align(Alignment.TopStart)
            )

            // Звезды рейтинга
            Row(modifier = Modifier.align(Alignment.TopEnd)) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = "Звезда рейтинга",
                        tint = if (index < filledStars) Color(0xFFFFD700) else Color(0xFFCCCCCC),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Дата и время
            Text(
                text = SimpleDateFormat("dd MMMM", Locale.getDefault())
                    .format(Date(attempt.timestamp)),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                modifier = Modifier.align(Alignment.BottomStart)
            )

            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault())
                    .format(Date(attempt.timestamp)),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

    }
}