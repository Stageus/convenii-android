@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.CommentUi
import com.example.convenii.view.components.ConfirmBtn


@Composable
fun ReviewDetailScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
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
            if (true) {
                ConfirmBtn(
                    text = "리뷰 남기기",
                    enabled = true,
                    onClick = { /*TODO*/ },
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
            val sample = List(20) {
                CommentSampleData(
                    idx = 1,
                    productIdx = 1,
                    nickname = "닉네임",
                    score = 4,
                    content = "리뷰 내용",
                    created_at = "2021.09.01"
                )
            }
            Column {
                LazyColumn {
                    items(sample.size) { item ->
                        if (item != 0) {
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                        CommentUi(
                            nickname = sample[item].nickname,
                            star = sample[item].score,
                            comment = sample[item].content,
                            date = sample[item].created_at,
                        )
                    }
                }
            }
        }

    }


}