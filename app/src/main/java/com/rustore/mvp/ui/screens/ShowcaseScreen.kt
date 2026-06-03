package com.rustore.mvp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rustore.mvp.data.models.AppItem
import com.rustore.mvp.data.models.Category
import com.rustore.mvp.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseScreen(
    viewModel: AppViewModel,
    selectedCategory: Category? = null,
    onAppClick: (String) -> Unit,
    onCategoryChange: (Category?) -> Unit,
    onShowAllCategoriesClick: () -> Unit // <-- Новый параметр для кнопки
) {
    val allApps by viewModel.apps.collectAsState()

    var currentCategory by remember { mutableStateOf(selectedCategory) }

    LaunchedEffect(selectedCategory) {
        currentCategory = selectedCategory
    }

    val displayedApps = if (currentCategory != null) {
        allApps.filter { it.category == currentCategory }
    } else {
        allApps
    }

    val selectedIndex = if (currentCategory == null) {
        0
    } else {
        Category.values().indexOf(currentCategory) + 1
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RuStore", fontWeight = FontWeight.Bold, color = Color(0xFF1E88E5)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                actions = {
                    // Кнопка перехода к полному списку категорий
                    TextButton(onClick = onShowAllCategoriesClick) {
                        Text("Категории", color = Color(0xFF1E88E5), fontWeight = FontWeight.Medium)
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            TabRow(
                selectedTabIndex = selectedIndex,
                containerColor = Color.White,
                contentColor = Color(0xFF1E88E5)
            ) {
                Tab(
                    selected = currentCategory == null,
                    onClick = {
                        currentCategory = null
                        onCategoryChange(null)
                    },
                    text = {
                        Text("Все", fontWeight = if (currentCategory == null) FontWeight.Bold else FontWeight.Normal)
                    }
                )

                Category.values().forEach { category ->
                    Tab(
                        selected = currentCategory == category,
                        onClick = {
                            currentCategory = category
                            onCategoryChange(category)
                        },
                        text = {
                            Text(category.displayName, fontWeight = if (currentCategory == category) FontWeight.Bold else FontWeight.Normal)
                        }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(displayedApps) { app ->
                    ModernAppCard(app = app, onClick = { onAppClick(app.id) })
                }
            }
        }
    }
}

@Composable
fun ModernAppCard(app: AppItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                shadowElevation = 4.dp,
                color = Color.White
            ) {
                AppIconWithFallback(
                    url = app.iconUrl,
                    name = app.name,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    app.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    app.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("4.8", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.width(8.dp))
                    Text(app.category.displayName, fontSize = 12.sp, color = Color(0xFF1E88E5))
                }
            }

            TextButton(onClick = {}) {
                Text("→", fontSize = 20.sp, color = Color(0xFF1E88E5))
            }
        }
    }
}

@Composable
fun AppIconWithFallback(url: String, name: String, modifier: Modifier = Modifier) {
    var loadFailed by remember { mutableStateOf(false) }
    if (!loadFailed) {
        AsyncImage(
            model = url,
            contentDescription = name,
            modifier = modifier.clip(CircleShape),
            onError = { loadFailed = true }
        )
    }
    if (loadFailed) {
        Surface(modifier = modifier, shape = CircleShape, color = Color(0xFFE3F2FD)) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = name.firstOrNull()?.uppercase() ?: "?", color = Color(0xFF1E88E5), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
        }
    }
}