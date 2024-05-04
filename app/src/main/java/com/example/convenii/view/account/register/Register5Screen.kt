@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.account.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.model.APIResponse
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.AccountInputField
import com.example.convenii.view.components.ConfirmBtn
import com.example.convenii.viewModel.account.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register5Screen(
    navController: NavController,
    viewModel: RegisterViewModel
) {

    var nickname by remember { mutableStateOf("") }
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(key1 = registerState) {
        if (registerState is APIResponse.Success) {
            navController.navigate("home") {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
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
                        Text(
                            text = "회원가입",
                            modifier = Modifier.align(Alignment.Center),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "닉네임",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                AccountInputField(
                    keyboardOptions = KeyboardOptions.Default,
                    isPassword = false,
                    text = nickname,
                    valueChange = {
                        nickname = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    placeholder = "닉네임을 입력해주세요",
                    isError = false
                )
                if (registerState is APIResponse.Error && (registerState as APIResponse.Error).errorCode == "400") {
                    Text(
                        text = "중복된 닉네임입니다",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            color = Color.Red
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .padding(start = 5.dp)
                    )
                }

                //간격 최대
                Spacer(modifier = Modifier.weight(1f))
                ConfirmBtn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .navigationBarsPadding(),
                    text = "완료",
                    enabled = nickname.isNotEmpty(),
                    onClick = {
                        viewModel.register(nickname)

                    }

                )
            }
        }
    }
}