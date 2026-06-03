package com.rustore.mvp.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rustore.mvp.data.models.Category
import com.rustore.mvp.ui.screens.*
import com.rustore.mvp.ui.viewmodel.AppViewModel
import com.rustore.mvp.ui.viewmodel.OnboardingViewModel

@Composable
fun RuStoreNavHost() {
    val navController = rememberNavController()

    val onboardingVM: OnboardingViewModel = viewModel()
    val appVM: AppViewModel = viewModel()

    val hasSeen by onboardingVM.hasSeenOnboarding.collectAsState()

    val startDest = if (hasSeen) Screen.ShowcaseAll.route else Screen.Onboarding.route

    NavHost(navController, startDestination = startDest) {

        composable(Screen.Onboarding.route) {
            OnboardingScreen {
                onboardingVM.markOnboardingAsSeen()
                navController.navigate(Screen.ShowcaseAll.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            }
        }

        // Витрина ВСЕХ приложений
        composable(Screen.ShowcaseAll.route) {
            ShowcaseScreen(
                viewModel = appVM,
                selectedCategory = null,
                onAppClick = { navController.navigate(Screen.AppDetail.createRoute(it)) },
                onCategoryChange = { },
                onShowAllCategoriesClick = {
                    navController.navigate(Screen.Categories.route)
                }
            )
        }

        // Витрина по КОНКРЕТНОЙ категории
        composable(
            route = Screen.ShowcaseCategory.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            val category = try {
                categoryName?.let { Category.valueOf(it) }
            } catch (e: Exception) {
                null
            }

            ShowcaseScreen(
                viewModel = appVM,
                selectedCategory = category,
                onAppClick = { navController.navigate(Screen.AppDetail.createRoute(it)) },
                onCategoryChange = { },
                onShowAllCategoriesClick = {
                    navController.navigate(Screen.Categories.route)
                }
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                viewModel = appVM,
                onBackClick = { navController.popBackStack() },
                onCategoryClick = { cat ->
                    navController.navigate(Screen.ShowcaseCategory.createRoute(cat.name))
                }
            )
        }

        composable(Screen.AppDetail.route,
            arguments = listOf(navArgument("appId") { type = NavType.StringType })) {
            val appId = it.arguments?.getString("appId") ?: return@composable
            AppDetailScreen(
                appId = appId,
                viewModel = appVM,
                onBackClick = { navController.popBackStack() },
                onScreenshotClick = {
                    navController.navigate(Screen.FullscreenScreenshots.createRoute(appId, it))
                }
            )
        }

        composable(Screen.FullscreenScreenshots.route,
            arguments = listOf(
                navArgument("appId") { type = NavType.StringType },
                navArgument("index") { type = NavType.IntType }
            )) {
            val appId = it.arguments?.getString("appId") ?: return@composable
            val index = it.arguments?.getInt("index") ?: 0
            FullscreenScreenshotsScreen(
                appId = appId,
                initialIndex = index,
                viewModel = appVM,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}