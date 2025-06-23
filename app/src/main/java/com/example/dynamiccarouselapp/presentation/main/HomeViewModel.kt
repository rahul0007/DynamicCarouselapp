package com.example.dynamiccarouselapp.presentation.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dynamiccarouselapp.data.CategoryDataProvider
import com.example.dynamiccarouselapp.domain.model.CategoryPage
import com.example.dynamiccarouselapp.domain.model.ListItemData
class HomeViewModel : ViewModel() {

    // List of category pages with associated items and images (static dummy data)
    val categoryPages: List<CategoryPage> = CategoryDataProvider.getAllCategories()

    // --- State: Currently selected category page index
    private val _currentPageIndex = MutableLiveData(0)
    val currentPageIndex: LiveData<Int> = _currentPageIndex

    // --- State: Search query entered by the user
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    // --- State: Filtered list of items shown in the UI
    private val _filteredItemList = MutableLiveData<List<ListItemData>>()
    val filteredItemList: LiveData<List<ListItemData>> = _filteredItemList

    // Called when ViewModel is created – filters initial list
    init {
        filterItems()
    }
    /**
     * Updates the search query and refreshes the filtered item list.
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterItems()
    }

    /**
     * Updates the selected page index and refreshes the filtered item list.
     */
    fun onPageChanged(newPageIndex: Int) {
        _currentPageIndex.value = newPageIndex
        filterItems()
    }

    /**
     * Filters the items based on the current page and search query.
     * If the query is blank, it shows all items of the selected page.
     * Otherwise, filters items where title or subtitle starts with the query.
     */

    private fun filterItems() {
        val query = _searchQuery.value.orEmpty().trim().lowercase()
        val pageIndex = _currentPageIndex.value ?: 0

        // Get all items from the selected page
        val allItems = categoryPages.getOrNull(pageIndex)?.items.orEmpty()

        // Apply filtering based on query
        _filteredItemList.value = if (query.isBlank()) {
            allItems
        } else {
            allItems.filter {
                it.title.lowercase().startsWith(query) || it.subtitle.lowercase().startsWith(query)
            }
        }
    }

    /**
     * Returns a formatted statistics string showing:
     * - Number of items in the current or filtered list
     * - Top 3 most frequent characters in item titles
     */

    fun getStatisticsText(): String {
        val query = _searchQuery.value.orEmpty().trim()
        val pageIndex = _currentPageIndex.value ?: 0

        // Get either full list or filtered list based on search
        val fullList = categoryPages.getOrNull(pageIndex)?.items.orEmpty()
        val activeList = if (query.isBlank()) {
            fullList
        } else {
            _filteredItemList.value.orEmpty()
        }

        // Count character frequencies in item titles
        val frequencyMap = mutableMapOf<Char, Int>()
        for (item in activeList) {
            item.title.lowercase().forEach { ch ->
                if (ch.isLetter()) {
                    frequencyMap[ch] = frequencyMap.getOrDefault(ch, 0) + 1
                }
            }
        }

        // Get top 3 most frequent characters
        val topFrequencies = frequencyMap.entries
            .sortedByDescending { it.value }
            .take(3)
            .joinToString("\n") { "${it.key} = ${it.value}" }

        // Prepare the result string
        val title = if (query.isBlank()) {
            "${categoryPages[pageIndex].title} (${fullList.size} items)"
        } else {
            "Search Results (${activeList.size} items)"
        }

        return "$title\n$topFrequencies"
    }
}


