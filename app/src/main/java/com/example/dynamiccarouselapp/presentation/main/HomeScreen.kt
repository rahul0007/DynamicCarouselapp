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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.dynamiccarouselapp.core.util.Dimens
import com.example.dynamiccarouselapp.presentation.components.ImageCarousel
import com.example.dynamiccarouselapp.presentation.components.SearchBar
import com.example.dynamiccarouselapp.ui.theme.DotColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = remember { HomeViewModel() }) {
    val items by viewModel.filteredItems
    val query by viewModel.searchQuery
    val currentPage by viewModel.currentPage
    val categories = viewModel.allPages

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSheet = remember { mutableStateOf(false) }

    if (showSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showSheet.value = false },
            sheetState = bottomSheetState
        ) {
            Text(
                text = viewModel.getStatistics(),
                modifier = Modifier.padding(Dimens.PaddingL),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSheet.value = true },
                containerColor = DotColor,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Stats",
                    tint = Color.White
                )
            }
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding),
            contentPadding = PaddingValues(Dimens.PaddingL)
        ) {
            item {
                ImageCarousel(
                    categories = categories,
                    currentPage = currentPage,
                    onPageChanged = viewModel::onPageChanged
                )
            }

            stickyHeader {
                Surface(color = Color.White) {
                    SearchBar(
                        query = query,
                        onQueryChanged = viewModel::onSearchQueryChanged
                    )
                }
            }

            items(items) { item ->
                ListItemCard(
                    title = item.title,
                    subtitle = item.subtitle,
                    iconResId = item.imageResId
                )
            }
        }
    }
}