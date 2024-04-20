@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.ConfirmBtn

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ReviewAddScreen(navController: NavController) {
    //변경될 수 있는 score 값
    var star by remember { mutableIntStateOf(1) }
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
                    ) {
                        Text(
                            text = "",
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.test), contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth(0.41f)
                        .aspectRatio(1f)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.FillBounds,
                )

                Text(
                    text = "상품명",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pretendard
                    ),
                    modifier = Modifier.padding(top = 24.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    for (i in 1..star) {
                        if (i != 1) Spacer(modifier = Modifier.padding(start = 4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.icon_fill_star),
                            contentDescription = null,
                            tint = Color(0xffFFD643),
                            modifier = Modifier
                                .size(36.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) { star = i }
                        )
                    }

                    for (i in 1..(5 - star)) {
                        Spacer(modifier = Modifier.padding(start = 4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.icon_empty_star),
                            contentDescription = null,
                            tint = Color(0xffFFD643),
                            modifier = Modifier
                                .size(36.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    star += i
                                    Log.d("star", star.toString())
                                }

                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                var tmp = ""

                BasicTextField(
                    value = tmp, onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xffffffff))
                                .border(1.dp, Color(0xffD1D1D1), RoundedCornerShape(8.dp))
                        ) {
                            if (tmp.isEmpty()) {
                                Text(
                                    text = "리뷰를 작성해주세요",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xff8D8D8D),
                                        fontFamily = pretendard,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            innerTextField()
                        }
                    }
                )


            }  //column
        }


    }

}