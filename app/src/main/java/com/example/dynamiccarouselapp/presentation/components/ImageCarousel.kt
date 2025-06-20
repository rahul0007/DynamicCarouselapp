package com.example.dynamiccarouselapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dynamiccarouselapp.core.util.Dimens
import com.example.dynamiccarouselapp.domain.model.CategoryPage
import com.example.dynamiccarouselapp.ui.theme.DotColor
import com.example.dynamiccarouselapp.ui.theme.LightGray

@Composable
fun ImageCarousel(
    categories: List<CategoryPage>,
    currentPage: Int,
    onPageChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = currentPage,
        pageCount = { categories.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.CarouselHeight)
                .clip(RoundedCornerShape(Dimens.BorderRadius))
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Image(
                    painter = painterResource(categories[page].baseImages),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.PaddingL))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.PaddingS),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(categories.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = Dimens.DotSpacing)
                        .size(Dimens.DotSize)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) DotColor else LightGray
                        )
                )
            }
        }
    }
}
