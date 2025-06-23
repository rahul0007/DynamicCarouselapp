package com.example.dynamiccarouselapp.presentation.main

import ListItemCard
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.dynamiccarouselapp.core.util.Dimens
import com.example.dynamiccarouselapp.presentation.components.ImageCarousel
import com.example.dynamiccarouselapp.presentation.components.SearchBar
import com.example.dynamiccarouselapp.ui.theme.DotColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = remember { HomeViewModel() }) {

    // --- Observe state from ViewModel ---
    val itemList by viewModel.filteredItemList.observeAsState(emptyList())     // Filtered list of items
    val searchQuery by viewModel.searchQuery.observeAsState("")                // Current search query
    val selectedPage by viewModel.currentPageIndex.observeAsState(0)           // Currently selected page index
    val categoryPages = viewModel.categoryPages                                // All category data

    // --- Bottom sheet state ---
    val showBottomSheet = remember { mutableStateOf(false) }                   // Controls whether bottom sheet is visible
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // --- Bottom Sheet Content ---
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },              // Close sheet on dismiss
            sheetState = sheetState
        ) {
            Text(
                text = viewModel.getStatisticsText(),                          // Show statistics string
                modifier = Modifier.padding(Dimens.PaddingL),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    // --- Main Scaffold Layout ---
    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet.value = true },                    // Show bottom sheet on FAB click
                containerColor = DotColor,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Show Stats",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        // --- Main Content: LazyColumn with carousel, search bar, and item list ---
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(Dimens.PaddingL)
        ) {
            // --- Image carousel at the top ---
            item {
                ImageCarousel(
                    categories = categoryPages,
                    currentPage = selectedPage,
                    onPageChanged = viewModel::onPageChanged                  // Update page on swipe
                )
            }

            // --- Sticky Search Bar Header ---
            stickyHeader {
                Surface(color = Color.White) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChanged = viewModel::onSearchQueryChanged      // Update query on input change
                    )
                }
            }

            // --- Display filtered items as a list ---
            items(itemList) { item ->
                ListItemCard(
                    title = item.title,
                    subtitle = item.subtitle,
                    iconResId = item.imageResId
                )
            }
        }
    }
}
