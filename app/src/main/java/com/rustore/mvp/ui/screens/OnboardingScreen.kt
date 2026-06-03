package com.rustore.mvp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(onNavigateToShowcase: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF1E88E5))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("RuStore", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(16.dp))
            Text("Добро пожаловать в RuStore!", fontSize = 24.sp, color = Color.White)
            Spacer(Modifier.height(48.dp))
            Button(
                onClick = onNavigateToShowcase,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Перейти к приложениям", fontSize = 18.sp,
                     color = Color(0xFF1E88E5), fontWeight = FontWeight.Bold)
            }
        }
    }
}