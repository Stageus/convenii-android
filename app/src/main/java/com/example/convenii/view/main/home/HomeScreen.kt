package com.example.convenii.view.main.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.BottomNav
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val tabTitles = listOf("GS", "CU", "e-mart")
    val pagerState = rememberPagerState {
        tabTitles.size
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) {
        Column {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White,
                divider = {
                },
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Color.Black,
                        height = 1.dp
                    )
                },
                contentColor = Color.Black,
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
                                modifier = Modifier.padding(16.dp),
                                color = Color.Black,
                                style = androidx.compose.ui.text.TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        })
                }
            }

            HorizontalPager(state = pagerState) { page ->
                Text(
                    text = "ee", modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                )

            }

        }
    }
}