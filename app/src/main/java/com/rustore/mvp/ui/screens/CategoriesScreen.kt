package com.rustore.mvp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rustore.mvp.data.models.Category
import com.rustore.mvp.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: AppViewModel,
    onBackClick: () -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Категории", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories.entries.toList()) { (category, count) ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onCategoryClick(category) },
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), 
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(category.displayName, fontWeight = FontWeight.Medium)
                        Text("$count приложений", color = Color.Gray)
                    }
                }
            }
        }
    }
}