package com.example.dailyquiz.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyquiz.R
import com.example.dailyquiz.domain.models.QuizAttempt

@Composable
fun HistoryList(
    attempts: List<QuizAttempt>,
    onDeleteAttempt: (String) -> Unit,
    navController: NavController,
    selectedAttempt: QuizAttempt?,
    onSelectedAttemptChange: (QuizAttempt?) -> Unit,
    onSelectedItemPositionChange: (Offset?) -> Unit = {}
) {
    val uniqueAttempts = remember(attempts) {
        attempts.distinctBy { it.attemptId }
            .sortedByDescending { it.timestamp }
    }

    val itemPositions = remember { mutableStateMapOf<String, LayoutCoordinates>() }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(uniqueAttempts, key = { it.attemptId }) { attempt ->
                val itemRef = remember { mutableStateOf<LayoutCoordinates?>(null) }
                val isSelected = selectedAttempt?.attemptId == attempt.attemptId

                Box(
                    modifier = Modifier
                        .onPlaced { coordinates ->
                            itemRef.value = coordinates
                            itemPositions[attempt.attemptId] = coordinates

                            // Обновляем позицию при выборе
                            if (isSelected) {
                                val position = coordinates.localToRoot(Offset.Zero)
                                onSelectedItemPositionChange(position)
                            }
                        }
                ) {
                    HistoryItem(
                        attempt = attempt,
                        onItemClick = {
                            if (selectedAttempt != null) {
                                onSelectedAttemptChange(null)
                                onSelectedItemPositionChange(null)
                            } else {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "reviewQuiz",
                                    attempt
                                )
                                navController.navigate("review")
                            }
                        },
                        onItemLongClick = {
                            if (isSelected) {
                                onSelectedAttemptChange(null)
                                onSelectedItemPositionChange(null)
                            } else {
                                onSelectedAttemptChange(attempt)
                            }
                        },
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}