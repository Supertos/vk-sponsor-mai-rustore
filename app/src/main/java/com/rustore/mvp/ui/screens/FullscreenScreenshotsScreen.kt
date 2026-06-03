package com.rustore.mvp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.rustore.mvp.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FullscreenScreenshotsScreen(
    appId: String,
    initialIndex: Int,
    viewModel: AppViewModel,
    onBackClick: () -> Unit
) {
    val app = remember(appId) { viewModel.getAppById(appId) } ?: return

    // ИСПРАВЛЕНО: используем screenshotUrls вместо screenshots
    val pagerState = rememberPagerState(initialPage = initialIndex) { app.screenshotUrls.size }

    Scaffold(
        topBar = {
            TopAppBar(
                // ИСПРАВЛЕНО: используем screenshotUrls для расчета количества
                title = { Text("${pagerState.currentPage + 1}/${app.screenshotUrls.size}") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding).fillMaxSize()
        ) { page ->
            // ИСПРАВЛЕНО: берем URL из списка screenshotUrls
            AsyncImage(
                model = app.screenshotUrls[page],
                contentDescription = "Screenshot ${page + 1}",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}