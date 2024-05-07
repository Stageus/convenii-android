@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.model.APIResponse
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.CommentUi
import com.example.convenii.view.components.ConfirmBtn
import com.example.convenii.viewModel.detail.DetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    navController: NavController,
    productIdx: String?
) {
    val viewModel: DetailViewModel = hiltViewModel()
    val reviewDataState = viewModel.productReviewState.collectAsState()
    val reviewData = viewModel.reviewData.collectAsState()
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = true) {
        viewModel.getProductReviewDetail(productIdx!!.toInt())
    }
    //무한 스크롤
    LaunchedEffect(lazyListState, viewModel.isReviewDataEnded) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                if (lastVisibleItem != null && lastVisibleItem.index >= reviewData.value.size - 1 && !viewModel.isReviewDataEnded.value && !viewModel.isDataLoading.value) {
                    // 마지막 아이템이 화면에 보이는 경우 추가 데이터 로드
                    viewModel.getProductReviewDetail(productIdx!!.toInt())
                }
            }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "리뷰",
                            modifier = Modifier.align(Alignment.Center),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (reviewDataState.value !is APIResponse.Empty && reviewDataState.value.data!!.authStatus != "false") {
                ConfirmBtn(
                    text = "리뷰 남기기",
                    enabled = true,
                    onClick = {
                        navController.navigate("reviewAdd")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 16.dp)
                )
            }

        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            //20개 반복 데이터 반복
            LazyColumn(
                userScrollEnabled = true,
                state = lazyListState,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(reviewData.value.size) { item ->
                    if (item != 0) {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    CommentUi(
                        nickname = reviewData.value[item].nickname,
                        star = reviewData.value[item].score,
                        comment = reviewData.value[item].content,
                        date = reviewData.value[item].createdAt,
                    )
                }


            }
        }

    }


}