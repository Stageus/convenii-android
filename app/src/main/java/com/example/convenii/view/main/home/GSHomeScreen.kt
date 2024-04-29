package com.example.convenii.view.main.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.model.APIResponse
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.MainCard
import com.example.convenii.viewModel.main.home.HomeViewModel

@Composable
fun GSHomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val homeGSData = viewModel.homeGSData.collectAsState()

    LaunchedEffect(true) {
        viewModel.getHomeProductCompanyData(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 16.dp)
            .padding(horizontal = 20.dp)

    ) {
        if (homeGSData.value is APIResponse.Success) {

            for (i in 0 until 3) {
                if (i != 0) Spacer(modifier = Modifier.padding(3.dp))
                MainCard(
                    name = homeGSData.value.data!!.data.productList[i].name,
                    price = homeGSData.value.data!!.data.productList[i].price,
                    bookmarked = homeGSData.value.data!!.data.productList[i].bookmarked,
                    events = homeGSData.value.data!!.data.productList[i].events,
                    clickEvent = {
                        Log.d(
                            "GSHomeScreen",
                            homeGSData.value.data!!.data.productList[i].events.toString()
                        )
                        // TODO
                    },
                    productImg = homeGSData.value.data!!.data.productList[i].productImg,
                )
            }
        }

        Text(
            text = "더보기", style = TextStyle(
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
                },
            textAlign = TextAlign.End
        )
    }

}