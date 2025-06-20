package com.example.dynamiccarouselapp.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.ViewModel
import com.example.dynamiccarouselapp.R
import com.example.dynamiccarouselapp.domain.model.CategoryPage
import com.example.dynamiccarouselapp.domain.model.ListItemData

class HomeViewModel : ViewModel() {

    val allPages = listOf(
        CategoryPage(
            title = "Fruits",
            baseImages = R.drawable.fruits,
            items = listOf(
                ListItemData("Apple", "Fresh and juicy", R.drawable.apple),
                ListItemData("Banana", "Rich in potassium", R.drawable.banana),
                ListItemData("Orange", "Full of Vitamin C", R.drawable.orange),
                ListItemData("Pineapple", "Tropical and tangy", R.drawable.pineapple),
                ListItemData("Grapes", "Sweet and seedless", R.drawable.grapes),
                ListItemData("Mango", "King of fruits", R.drawable.mango),
                ListItemData("Watermelon", "Refreshing and hydrating", R.drawable.watermelon),
                ListItemData("Papaya", "Soft and nutritious", R.drawable.papaya),
                ListItemData("Guava", "Rich in Vitamin C", R.drawable.guava),
                ListItemData("Lychee", "Sweet and aromatic", R.drawable.lychee),
                ListItemData("Pomegranate", "Juicy with seeds", R.drawable.pomegranate)
            )
        ),
        CategoryPage(
            title = "Vegetables",
            baseImages = R.drawable.vegetables,
            items = listOf(
                ListItemData("Carrot", "Good for eyes", R.drawable.carrot),
                ListItemData("Tomato", "Rich in lycopene", R.drawable.tomato),
                ListItemData("Spinach", "Iron rich", R.drawable.spinach),
                ListItemData("Broccoli", "High in fiber", R.drawable.broccoli),
                ListItemData("Potato", "Starchy and filling", R.drawable.potato),
                ListItemData("Onion", "Essential in cooking", R.drawable.onion),
                ListItemData("Cucumber", "Cool and crunchy", R.drawable.cucumber),
                ListItemData("Peas", "Green and sweet", R.drawable.peas)
            )
        ),
        CategoryPage(
            title = "Berries",
            baseImages = R.drawable.berries,
            items = listOf(
                ListItemData("Strawberry", "Sweet and red", R.drawable.strawberry),
                ListItemData("Blueberry", "Antioxidant-rich", R.drawable.blueberry),
                ListItemData("Raspberry", "Tart and tasty", R.drawable.raspberry)
            )
        )
    )

    // --- 2. Current page index
    private val _currentPage = mutableStateOf(0)
    val currentPage: State<Int> = _currentPage

    // --- 3. Search query state
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    // --- 4. Filtered items based on query + current page


    val filteredItems: State<List<ListItemData>> = derivedStateOf {
        val query = _searchQuery.value.trim().lowercase()
        val items = allPages[_currentPage.value].items
        if (query.isBlank()) items
        else items.filter {
            it.title.lowercase().startsWith(query) || it.subtitle.lowercase().startsWith(query)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onPageChanged(page: Int) {
        _currentPage.value = page
    }

    fun getStatistics(): String {
        val items = allPages[_currentPage.value].items
        val charMap = mutableMapOf<Char, Int>()
        items.forEach {
            (it.title).lowercase().forEach { ch ->
                if (ch.isLetter()) charMap[ch] = charMap.getOrDefault(ch, 0) + 1
            }
        }
        val topChars = charMap.entries.sortedByDescending { it.value }.take(3)
        val topStats = topChars.joinToString("\n") { "${it.key} = ${it.value}" }
        return "${allPages[_currentPage.value].title} (${items.size} items)\n$topStats"
    }
}
