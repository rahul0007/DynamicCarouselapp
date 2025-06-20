package com.example.dynamiccarouselapp.domain.model

data class CategoryPage(
    val title: String,
    val baseImages: Int,
    val items: List<ListItemData>
)

data class ListItemData(
    val title: String,
    val subtitle: String,
    val imageResId: Int
)