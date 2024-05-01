package com.example.convenii.view.main.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.view.components.MainCard
import com.example.convenii.viewModel.main.home.MoreViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreGSScreen(
    navController: NavController,
) {
    val viewModel: MoreViewModel = hiltViewModel()
    val moreData = viewModel.moreData.collectAsState()
    val moreDataState = viewModel.moreDataState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getProductCompanyData(1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.popBackStack()
                        Log.d("data", moreData.value.toString())

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = null,
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
                        GlideImage(
                            imageModel = { R.drawable.image_gs25 },
                            modifier = Modifier.size(40.dp)
                        )
                    }
                })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 16.dp)
                    .padding(horizontal = 20.dp)
            ) {

                items(moreData.value.size) {
                    if (it != 0) {
                        Spacer(modifier = Modifier.padding(3.dp))
                    }
                    MainCard(
                        name = moreData.value[it].name,
                        price = moreData.value[it].price,
                        bookmarked = moreData.value[it].bookmarked,
                        events = moreData.value[it].events,
                        clickEvent = {
                            navController.navigate("productDetail/${moreData.value[it].idx}")
                        },
                        productImg = moreData.value[it].productImg,
                    )
                }


            }


        }
    }
}