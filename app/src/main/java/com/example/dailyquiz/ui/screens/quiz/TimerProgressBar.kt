package com.example.dailyquiz.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dailyquiz.ui.theme.BorderColor

@Composable
fun TimerProgressBar(
    remainingTime: Int,
    totalTime: Int,
    modifier: Modifier = Modifier
) {
    val progress = remember(remainingTime) {
        remainingTime.toFloat() / totalTime.toFloat()
    }

    val minutes = remainingTime / 60
    val seconds = remainingTime % 60
    val timeFormatted = String.format("%d:%02d", minutes, seconds)

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        // Время слева и справа
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = timeFormatted,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BorderColor
            )

            Text(
                text = "5:00",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BorderColor
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Полоса прогресса
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.LightGray, RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .background(BorderColor, RoundedCornerShape(4.dp))
            )
        }
    }
}