package com.rustore.mvp.data.models

enum class Category(val displayName: String) {
    FINANCE("Финансы"),
    TOOLS("Инструменты"),
    GAMES("Игры"),
    GOVERNMENT("Государственные"),
    TRANSPORT("Транспорт")
}

enum class AgeRating(val displayName: String) {
    ZERO_PLUS("0+"),
    SIX_PLUS("6+"),
    EIGHT_PLUS("8+"),
    TWELVE_PLUS("12+"),
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+")
}

data class AppItem(
    val id: String,
    val name: String,
    val shortDescription: String,
    val fullDescription: String,
    val category: Category,
    val developer: String,
    val ageRating: AgeRating,
    val iconUrl: String, // Вернули URL
    val screenshotUrls: List<String> // Вернули URL скриншотов
)