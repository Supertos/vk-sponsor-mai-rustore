package com.rustore.mvp.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")

    // Важно: строка внутри кавычек должна быть уникальной и простой
    object ShowcaseAll : Screen("showcase_all")

    object ShowcaseCategory : Screen("showcase_category/{categoryName}") {
        fun createRoute(categoryName: String) = "showcase_category/$categoryName"
    }

    object Categories : Screen("categories")
    object AppDetail : Screen("app_detail/{appId}") {
        fun createRoute(appId: String) = "app_detail/$appId"
    }
    object FullscreenScreenshots : Screen("fullscreen_screenshots/{appId}/{index}") {
        fun createRoute(appId: String, index: Int) = "fullscreen_screenshots/$appId/$index"
    }
}