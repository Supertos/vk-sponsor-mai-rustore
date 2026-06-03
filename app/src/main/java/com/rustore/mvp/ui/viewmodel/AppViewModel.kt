package com.rustore.mvp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.rustore.mvp.data.models.AppItem
import com.rustore.mvp.data.models.Category
import com.rustore.mvp.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppViewModel : ViewModel() {
    private val repository = AppRepository()

    private val _apps = MutableStateFlow<List<AppItem>>(emptyList())
    val apps: StateFlow<List<AppItem>> = _apps

    private val _categories = MutableStateFlow<Map<Category, Int>>(emptyMap())
    val categories: StateFlow<Map<Category, Int>> = _categories

    init {
        _apps.value = repository.getApps()
        _categories.value = repository.getCategories()
    }

    fun getAppById(id: String): AppItem? = repository.getAppById(id)
}