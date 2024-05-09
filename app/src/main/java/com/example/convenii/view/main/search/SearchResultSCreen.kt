package com.example.convenii.view.main.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.MainCard
import com.example.convenii.viewModel.main.Search.SearchViewModel


@Composable
fun SearchResultScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val scrollState = rememberScrollState()
    val eventFilterState by viewModel.selectedEventFilter.collectAsState()
    val categoryFilterState by viewModel.selectedCategoryFilter.collectAsState()
    val searchDataState = viewModel.searchDataState.collectAsState()
    val searchData = viewModel.searchData.collectAsState()
    val lazyListState = rememberLazyListState()
    val keyword by viewModel.keyword.collectAsState()
    val rankIdx by viewModel.rankIdx.collectAsState()


    Box(
        modifier = Modifier
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

                if (rankIdx == 2) {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .clickable(enabled = true, onClick = {
                                navController.navigate("addProduct")
                            })
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
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done

                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.saveSearchHistory(keyword)
                        viewModel.resetData()
                        viewModel.getSearchData()
                    }
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 11.dp)
                    .padding(top = 20.dp)
            ) { // 이벤트 필터 버튼
                FilterButton(
                    text = "1 + 1",
                    filterState = eventFilterState.onePlus,
                    onClick = {
                        viewModel.toggleEventFilter("onePlus")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "2 + 1",
                    filterState = eventFilterState.twoPlus,
                    onClick = {
                        viewModel.toggleEventFilter("twoPlus")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "할인",
                    filterState = eventFilterState.sale,
                    onClick = {
                        viewModel.toggleEventFilter("sale")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "덤증정",
                    filterState = eventFilterState.bonus,
                    onClick = {
                        viewModel.toggleEventFilter("bonus")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "기타",
                    filterState = eventFilterState.other,
                    onClick = {
                        viewModel.toggleEventFilter("other")
                    })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 11.dp)
                    .padding(top = 20.dp)
            ) { // 이벤트 필터 버튼
                FilterButton(
                    text = "음료",
                    filterState = categoryFilterState.drink,
                    onClick = {
                        viewModel.toggleCategoryFilter("drink")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "과자",
                    filterState = categoryFilterState.snack,
                    onClick = {
                        viewModel.toggleCategoryFilter("snack")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "식품",
                    filterState = categoryFilterState.food,
                    onClick = {
                        viewModel.toggleCategoryFilter("food")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "아이스크림",
                    filterState = categoryFilterState.iceCream,
                    onClick = {
                        viewModel.toggleCategoryFilter("iceCream")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "생활용품",
                    filterState = categoryFilterState.daily,
                    onClick = {
                        viewModel.toggleCategoryFilter("daily")
                    })
                Spacer(modifier = Modifier.width(30.dp))
                FilterButton(
                    text = "기타",
                    filterState = categoryFilterState.other,
                    onClick = {
                        viewModel.toggleCategoryFilter("other")
                    })
            }


            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 16.dp),
                userScrollEnabled = true,
                state = lazyListState
            ) {
                items(searchData.value.size) {
                    if (it != 0) {
                        Spacer(modifier = Modifier.padding(3.dp))
                    }
                    MainCard(
                        name = searchData.value[it].name,
                        price = searchData.value[it].price,
                        bookmarked = searchData.value[it].bookmarked,
                        events = searchData.value[it].events,
                        clickEvent = {
                            navController.navigate("productDetail/${searchData.value[it].idx}")
                        },
                        productImg = searchData.value[it].productImg,
                    )
                }
            }


        } // main column


    }

}


@Composable
fun FilterButton(
    text: String,
    filterState: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            color = if (filterState) Color(0xff000000) else Color(0xff979797)
        ),
        modifier = Modifier.clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            indication = null
        ) { onClick() }
    )
}

