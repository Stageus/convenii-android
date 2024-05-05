package com.example.convenii.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.BottomNav


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "내정보",
                            modifier = Modifier.align(Alignment.Center),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                })
        },
        bottomBar = { BottomNav(navController = navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "이메일",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard,
                        color = Color(0xff646F7C)
                    )
                )

                Text(
                    text = "example@example.com",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                //구분선
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffF1F2F3))
                        .height(1.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(64.dp)
                ) {
                    Column {
                        Text(
                            text = "비밀번호",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = pretendard,
                                color = Color(0xff646F7C)
                            )
                        )

                        Text(
                            text = "********",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = pretendard,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    com.skydoves.landscapist.glide.GlideImage(
                        imageModel = {
                            R.drawable.icon_right_arrow
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }

                //구분선
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffF1F2F3))
                        .height(1.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(64.dp)
                ) {
                    Text(
                        text = "로그아웃",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = pretendard,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    com.skydoves.landscapist.glide.GlideImage(
                        imageModel = {
                            R.drawable.icon_right_arrow
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }

                //구분선
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffF1F2F3))
                        .height(1.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "회원탈퇴", style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = pretendard,
                            color = Color(0xff646F7C)
                        )
                    )

                }
            }// main column


        }
    }
}

