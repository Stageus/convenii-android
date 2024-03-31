package com.example.convenii.view.main.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val tabTitles = listOf("GS", "CU", "e-mart")
    val pagerState = rememberPagerState {
        tabTitles.size
    }
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            divider = {},
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = pagerState.currentPage == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                    text = {
                        Text(
                            text = tabTitles[index],
                            modifier = androidx.compose.ui.Modifier.padding(16.dp)
                        )
                    })
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Text(
                text = "ee", modifier = androidx.compose.ui.Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            )

        }

    }
}