@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.AccountInputField
import com.example.convenii.view.components.ConfirmBtn
import com.example.convenii.viewModel.SignInViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    parentEntry: NavBackStackEntry

) {
    val viewModel: SignInViewModel = hiltViewModel(parentEntry)

    val email by viewModel.email.collectAsState()
    val isEnabled by viewModel.isEnabled.collectAsState()
    val isFirst = remember { mutableStateOf(true) }
    val isError by viewModel.isError.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.setIsEnabled();
                        isFirst.value = !isFirst.value
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
                            text = "로그인",
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
                    .padding(top = 60.dp)
            ) {
                AccountInputField(
                    keyboardOptions = KeyboardOptions.Default,
                    isPassword = false,
                    text = email,
                    valueChange = { viewModel.setEmail(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = "이메일을 입력해주세요",
                    isError = isError
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                AccountInputField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isPassword = true,
                    text = email,
                    valueChange = { viewModel.setEmail(it) },
                    placeholder = "비밀번호를 입력해주세요",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    isError = isError
                )
                //간격 최대
                Spacer(modifier = Modifier.weight(1f))
                ConfirmBtn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                        .navigationBarsPadding(),
                    text = "로그인",
                    enabled = isEnabled,
                    onClick = {
//                        viewModel.setIsEnabled();
//                        viewModel.setIsError()
                        viewModel.signIn(email, "t")
                    }
                )
                if (isFirst.value) {
                    Text(
                        text = "처음 이용하시나요?", style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF646F7C),
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .clickable {
                                viewModel.setEmail("test")
                                navController.navigate("Register1")
                            }
                    )
                } else {
                    Text(
                        text = "비밀번호를 잊으셨나요?", style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF646F7C),
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }

            }
        }

    }

}


