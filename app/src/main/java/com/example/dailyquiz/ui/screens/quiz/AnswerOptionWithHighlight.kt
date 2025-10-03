package com.example.dailyquiz.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import com.example.dailyquiz.ui.theme.BorderColor
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor

@Composable
fun AnswerOptionWithHighlight(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isIncorrect: Boolean,
    onOptionSelected: () -> Unit,
    enabled: Boolean = true
) {
    val backgroundColor = when {
        isSelected -> Color.White
        else -> Color(0xFFF3F3F3)
    }

    val textColor = when {
        isCorrect -> Color(0xFF00B11E)
        isIncorrect -> Color(0xFFFD0000)
        isSelected -> BorderColor
        else -> Color.Black
    }

    val borderColor = when {
        isCorrect -> Color(0xFF00B11E)
        isIncorrect -> Color(0xFFFD0000)
        isSelected -> PrimaryBackgroundColor
        else -> Color.Transparent
    }

    val circleColor = when {
        isCorrect -> Color(0xFF00B11E)
        isIncorrect -> Color(0xFFFD0000)
        isSelected -> BorderColor
        else -> Color.Transparent
    }

    val circleBorderColor = when {
        isCorrect -> Color(0xFF00B11E)
        isIncorrect -> Color(0xFFFD0000)
        isSelected -> BorderColor
        else -> Color.Black
    }

    val showIcon = isCorrect || isIncorrect || isSelected

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onOptionSelected
            )
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        width = 1.0.dp,
                        color = circleBorderColor,
                        shape = CircleShape
                    )
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (showIcon) {
                    Icon(
                        imageVector = when {
                            isCorrect -> Icons.Filled.Check
                            isIncorrect -> Icons.Filled.Close
                            isSelected -> Icons.Filled.Check
                            else -> Icons.Filled.Check
                        },
                        contentDescription = when {
                            isCorrect -> "Верно"
                            isIncorrect -> "Неверно"
                            isSelected -> "Выбрано"
                            else -> "Выбрано"
                        },
                        tint = when {
                            isCorrect || isIncorrect -> Color.White
                            isSelected -> Color.White
                            else -> PrimaryBackgroundColor
                        },
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected || isCorrect || isIncorrect) FontWeight.SemiBold else FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
        }
    }
}