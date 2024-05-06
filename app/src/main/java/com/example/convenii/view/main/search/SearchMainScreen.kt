package com.example.convenii.view.main.search

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.BottomNav
import com.example.convenii.viewModel.main.Search.SearchViewModel


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SearchMainScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val keyword by viewModel.keyword.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.resetData()
        viewModel.resetFilter()
        viewModel.getSearchHistory()
    }
    Scaffold(
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 16.dp)
                .padding(top = 50.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "검색",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = pretendard
                        )
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .clickable(enabled = true, onClick = {})
                            .background(Color.White)
                            .height(30.dp)
                            .width(77.dp)
                            .border(
                                BorderStroke(1.dp, Color(0xffE6E8EB)),
                                shape = RoundedCornerShape(9.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "상품 추가",
                            color = Color(0xff646F7C),
                            fontSize = 11.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }



                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = keyword, onValueChange = { viewModel.setKeyword(it) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search),
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "검색어를 입력해주세요",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xffC5C8CE)
                            )
                        )
                    },
                    shape = RoundedCornerShape(12.dp),

                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = Color(0xFFFFFFFF),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedContainerColor = Color(0xFFFFFFFF),
                        //error에 따라 다른색상
                        focusedIndicatorColor = Color(0xff1F8CE6),
                        unfocusedIndicatorColor = Color(0xffE6E8EB), // 포커스 없
                        // 을 때 색상
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.saveSearchHistory(keyword)
                            viewModel.getSearchData()
                            navController.navigate("searchResult")
                        }
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "최근 검색어", style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff111111)
                    )
                )

                LazyColumn {
                    items(searchHistory.size) { index ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setKeyword(searchHistory[index])
                                    viewModel.getSearchData()
                                    viewModel.saveSearchHistory(searchHistory[index])
                                    navController.navigate("searchResult")
                                }
                        ) {
                            Text(
                                text = searchHistory[index],
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff646F7C)
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.icon_delete),
                                contentDescription = null,
                                modifier = Modifier.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    viewModel.deleteSearchHistory(searchHistory[index])
                                }
                            )
                        }

                    }
                }
            } // main column

        }

    }
}


