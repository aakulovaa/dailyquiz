package com.example.dailyquiz.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dailyquiz.R
import com.example.dailyquiz.ui.theme.WhiteContainerColor

@Composable
fun LoadingState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(66.dp))
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Логотип",
            tint = WhiteContainerColor,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        CircularProgressIndicator(color = Color.White)

    }
}