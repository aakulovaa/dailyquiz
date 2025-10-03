package com.example.dailyquiz.ui.screens.main

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyquiz.R
import com.example.dailyquiz.data.viewModels.quiz.QuizViewModel
import com.example.dailyquiz.ui.theme.PrimaryBackgroundColor
import com.example.dailyquiz.ui.theme.WhiteContainerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    onStartQuiz: () -> Unit,
    onHistory: () -> Unit,
    viewModel: QuizViewModel
) {
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedDifficulty by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // Кнопка "История" в верхней части
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 66.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onHistory,
                colors = ButtonDefaults.buttonColors(
                    containerColor = WhiteContainerColor,
                    contentColor = PrimaryBackgroundColor
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("История 🕒", fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Логотип приложения
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Логотип приложения",
            tint = WhiteContainerColor,
            modifier = Modifier.size(300.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-60).dp)
                .clip(RoundedCornerShape(24.dp)),
            color = WhiteContainerColor,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Добро пожаловать в DailyQuiz!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Фильтр категории
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedCategory,
                        onExpandedChange = { expandedCategory = !expandedCategory }
                    ) {
                        TextField(
                            value = viewModel.categories.find { it.first == viewModel.selectedCategory }?.second ?: "",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(
                                    width = 1.dp,
                                    color = PrimaryBackgroundColor,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            label = { Text("Категория") },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                disabledTextColor = Color.Black,
                                focusedLabelColor = PrimaryBackgroundColor,
                                unfocusedLabelColor = Color.Gray,
                                focusedTrailingIconColor = PrimaryBackgroundColor,
                                unfocusedTrailingIconColor = Color.Gray,
                                disabledContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedCategory,
                            onDismissRequest = { expandedCategory = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            viewModel.categories.forEach { (id, name) ->
                                DropdownMenuItem(
                                    text = { Text(name) },
                                    onClick = {
                                        viewModel.updateCategory(id)
                                        expandedCategory = false
                                    }
                                )
                            }
                        }
                    }
                }
                // Фильтр сложности
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedDifficulty,
                        onExpandedChange = { expandedDifficulty = !expandedDifficulty }
                    ) {
                        TextField(
                            value = viewModel.difficulties.find { it.first == viewModel.selectedDifficulty }?.second ?: "",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDifficulty) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(
                                    width = 1.dp,
                                    color = PrimaryBackgroundColor,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            label = { Text("Сложность") },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                disabledTextColor = Color.Black,
                                focusedLabelColor = PrimaryBackgroundColor,
                                unfocusedLabelColor = Color.Gray,
                                focusedTrailingIconColor = PrimaryBackgroundColor,
                                unfocusedTrailingIconColor = Color.Gray,
                                disabledContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDifficulty,
                            modifier = Modifier.background(Color.White),
                            onDismissRequest = { expandedDifficulty = false }
                        ) {
                            viewModel.difficulties.forEach { (id, name) ->
                                DropdownMenuItem(
                                    text = { Text(name) },
                                    onClick = {
                                        viewModel.updateDifficulty(id)
                                        expandedDifficulty = false
                                    }
                                )
                            }
                        }
                    }
                }


                Button(
                    onClick = onStartQuiz,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 10.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBackgroundColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Text("Начать викторину".uppercase(), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}