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
    val itemList by viewModel.filteredItemList.observeAsState(emptyList())
    val searchQuery by viewModel.searchQuery.observeAsState("")
    val selectedPage by viewModel.currentPageIndex.observeAsState(0)
    val categoryPages = viewModel.categoryPages
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState
        ) {
            Text(
                text = viewModel.getStatisticsText(),
                modifier = Modifier.padding(Dimens.PaddingL),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet.value = true },
                containerColor = DotColor,
                shape = CircleShape
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Show Stats", tint = Color.White)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(Dimens.PaddingL)
        ) {
            item {
                ImageCarousel(
                    categories = categoryPages,
                    currentPage = selectedPage,
                    onPageChanged = viewModel::onPageChanged
                )
            }

            stickyHeader {
                Surface(color = Color.White) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChanged = viewModel::onSearchQueryChanged
                    )
                }
            }

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
