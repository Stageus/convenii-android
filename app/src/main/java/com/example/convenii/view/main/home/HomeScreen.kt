package com.example.convenii.view.main.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.BottomNav
import com.example.convenii.view.components.MainCard
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
                        } //tab 표시 이름
                    )
                }
            }

            val GScardList = listOf(
                "GS",
                "CU",
                "e-mart"
            )
            val CUcardList = listOf(
                "GS",
                "CU",
                "e-mart"
            )
            val eMartcardList = listOf(
                "GS",
                "CU",
                "e-mart"
            )


            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(top = 16.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    for (i in 0 until 3) {
                        if (i != 0) Spacer(modifier = Modifier.padding(3.dp))
                        if (page == 0) {
                            MainCard()
                        } else if (page == 1) {
                            MainCard()
                        } else if (page == 2) {
                            MainCard()
                        }
                    }

                    Text(
                        text = "더보기", style = androidx.compose.ui.text.TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium
                        ), modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (page == 0) {
                                    Log.d("HomeScreen", "GS 더보기")
                                } else if (page == 1) {
                                    Log.d("HomeScreen", "CU 더보기")
                                } else if (page == 2) {
                                    Log.d("HomeScreen", "e-mart 더보기")
                                }
                            },
                        textAlign = TextAlign.End
                    )

                }
                when (page) {
                    0 -> {
                        Text(text = "GS")
                    }

                    1 -> {
                        Text(text = "CU")
                    }

                    2 -> {
                        Text(text = "e-mart")
                    }
                }

            }

        }
    }
}

