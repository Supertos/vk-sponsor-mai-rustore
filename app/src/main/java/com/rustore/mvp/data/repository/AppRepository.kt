package com.rustore.mvp.data.repository

import com.rustore.mvp.data.models.*

class AppRepository {
    fun getApps(): List<AppItem> {
        return listOf(
            AppItem("1", "СберБанк Онлайн", "Мобильный банк",
                "Управление финансами, переводы, оплата ЖКХ.", Category.FINANCE, "Сбербанк",
                AgeRating.ZERO_PLUS,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Sber_Logo.svg/1024px-Sber_Logo.svg.png",
                listOf("https://via.placeholder.com/300x600/4CAF50/FFF?text=Screen1")),

            AppItem("2", "Яндекс.Карты", "Навигация",
                "Карты, навигатор, пробки и общественный транспорт.", Category.TRANSPORT, "Яндекс",
                AgeRating.ZERO_PLUS,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Yandex_Maps_icon.svg/1024px-Yandex_Maps_icon.svg.png",
                listOf("https://via.placeholder.com/300x600/FF5722/FFF?text=Map")),

            AppItem("3", "Госуслуги", "Государственные услуги",
                "Официальное приложение для доступа к государственным услугам.", Category.GOVERNMENT, "Минцифры",
                AgeRating.ZERO_PLUS,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Gosuslugi_logo.svg/1024px-Gosuslugi_logo.svg.png",
                listOf("https://via.placeholder.com/300x600/2196F3/FFF?text=Gos")),

            AppItem("4", "Clash of Clans", "Стратегия",
                "Эпическая боевая стратегия.", Category.GAMES, "Supercell",
                AgeRating.EIGHT_PLUS,
                "https://upload.wikimedia.org/wikipedia/en/5/59/Clash_of_Clans_Logo.png",
                listOf("https://via.placeholder.com/300x600/9C27B0/FFF?text=Game")),

            AppItem("5", "ES Проводник", "Файловый менеджер",
                "Управление файлами.", Category.TOOLS, "ES Global",
                AgeRating.ZERO_PLUS,
                "https://cdn-icons-png.flaticon.com/512/2857/2857406.png", // Пример иконки папки
                listOf("https://via.placeholder.com/300x600/607D8B/FFF?text=Files"))
        )
    }

    fun getAppById(id: String): AppItem? = getApps().find { it.id == id }

    fun getCategories(): Map<Category, Int> {
        val apps = getApps()
        return Category.values().associateWith { cat -> apps.count { it.category == cat } }
    }
}