package com.rustore.mvp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rustore.mvp.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailScreen(
    appId: String,
    viewModel: AppViewModel,
    onBackClick: () -> Unit,
    onScreenshotClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val app = remember(appId) { viewModel.getAppById(appId) } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") }, // Пустой заголовок для чистоты
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            // --- HERO SECTION С ГРАДИЕНТОМ ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF1E88E5), Color(0xFF64B5F6))
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(80.dp),
                            shape = RoundedCornerShape(16.dp),
                            shadowElevation = 8.dp,
                            color = Color.White
                        ) {
                            AppIconWithFallback(
                                url = app.iconUrl,
                                name = app.name,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(app.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            Text(app.developer, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                }
            }

            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

                // Статистика и рейтинг
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    Column {
                        Text("4.8", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Рейтинг", color = Color.Gray, fontSize = 12.sp)
                    }
                    Column {
                        Text("1M+", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Загрузок", color = Color.Gray, fontSize = 12.sp)
                    }
                    Column {
                        Text(app.ageRating.displayName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Возраст", color = Color.Gray, fontSize = 12.sp)
                    }
                }

                // Кнопка установки
                Button(
                    onClick = { Toast.makeText(context, "Загрузка...", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                ) {
                    Text("Установить", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                Divider()

                // Скриншоты
                Text("Скриншоты", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(app.screenshotUrls) { index, url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Screen",
                            modifier = Modifier
                                .height(180.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onScreenshotClick(index) }
                        )
                    }
                }

                // Описание
                Text("О приложении", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    app.fullDescription,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}