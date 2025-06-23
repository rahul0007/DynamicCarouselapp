package com.example.dynamiccarouselapp.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dynamiccarouselapp.R
import com.example.dynamiccarouselapp.core.util.Dimens.FontLarge
import com.example.dynamiccarouselapp.domain.model.CategoryPage
import com.example.dynamiccarouselapp.domain.model.ListItemData

class HomeViewModel : ViewModel() {
    // Static category + items data
    val categoryPages: List<CategoryPage> = listOf(
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

    // Page and query state
    private val _currentPageIndex = MutableLiveData(0)
    val currentPageIndex: LiveData<Int> = _currentPageIndex

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _filteredItemList = MutableLiveData<List<ListItemData>>()
    val filteredItemList: LiveData<List<ListItemData>> = _filteredItemList

    init {
        filterItems()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterItems()
    }

    fun onPageChanged(newPageIndex: Int) {
        _currentPageIndex.value = newPageIndex
        filterItems()
    }

    private fun filterItems() {
        val query = _searchQuery.value.orEmpty().trim().lowercase()
        val pageIndex = _currentPageIndex.value ?: 0
        val allItems = categoryPages.getOrNull(pageIndex)?.items.orEmpty()

        _filteredItemList.value = if (query.isBlank()) {
            allItems
        } else {
            allItems.filter {
                it.title.lowercase().startsWith(query) || it.subtitle.lowercase().startsWith(query)
            }
        }
    }

    fun getStatisticsText(): String {
        val pageIndex = _currentPageIndex.value ?: 0
        val currentItems = categoryPages.getOrNull(pageIndex)?.items.orEmpty()

        val frequencyMap = mutableMapOf<Char, Int>()
        for (item in currentItems) {
            item.title.lowercase().forEach { char ->
                if (char.isLetter()) {
                    frequencyMap[char] = frequencyMap.getOrDefault(char, 0) + 1
                }
            }
        }

        val topFrequencies = frequencyMap.entries
            .sortedByDescending { it.value }
            .take(3)
            .joinToString("\n") { "${it.key} = ${it.value}" }

        val title = "${categoryPages[pageIndex].title} (${currentItems.size} items)"
        return "$title\n$topFrequencies"
    }
}



