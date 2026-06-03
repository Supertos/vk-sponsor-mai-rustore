package com.rustore.mvp.ui.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Наследуемся от AndroidViewModel, чтобы легко получить Application
class OnboardingViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs: SharedPreferences = application.getSharedPreferences("RuStorePrefs", Application.MODE_PRIVATE)
    private val key = "has_seen_onboarding"

    private val _hasSeenOnboarding = MutableStateFlow(prefs.getBoolean(key, false))
    val hasSeenOnboarding: StateFlow<Boolean> = _hasSeenOnboarding

    fun markOnboardingAsSeen() {
        prefs.edit().putBoolean(key, true).apply()
        _hasSeenOnboarding.value = true
    }
}