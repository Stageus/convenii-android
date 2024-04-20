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
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.AccountInputField
import com.example.convenii.view.components.ConfirmBtn
import com.example.convenii.viewModel.account.RegisterViewModel

@Composable
fun Register1Screen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    var email by remember { mutableStateOf("") }
    val isItEmail by viewModel.isItEmail.collectAsState()
    val isEmailSend by viewModel.isEmailSend.collectAsState()

    LaunchedEffect(key1 = isEmailSend) {
        if (isEmailSend) {
            navController.navigate("Register2")
            viewModel.resetIsEmailSend()
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
                    text = "이메일",
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
                    text = email,
                    valueChange = {
                        email = it
                        viewModel.checkIsItEmail(it)

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    placeholder = "이메일을 입력해주세요",
                    isError = !isItEmail
                )

                //간격 최대
                Spacer(modifier = Modifier.weight(1f))
                ConfirmBtn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .navigationBarsPadding(),
                    text = "다음으로",
                    enabled = isItEmail,
                    onClick = {
                        viewModel.verifyEmailSend(email)
//                        navController.navigate("Register2")

                    }
                )

            }
        }

    }

}