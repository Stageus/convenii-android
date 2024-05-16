package com.example.convenii.view.profile

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.model.APIResponse
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.ConveniiScreen
import com.example.convenii.view.components.BottomNav
import com.example.convenii.view.components.CustomConfirmDialog
import com.example.convenii.view.components.CustomSelectDialog
import com.example.convenii.viewModel.account.ChangePwViewModel
import com.example.convenii.viewModel.account.RegisterViewModel
import com.example.convenii.viewModel.profile.ProfileViewModel


@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel,
    profileViewModel: ProfileViewModel
) {
    val changePwViewModel: ChangePwViewModel = hiltViewModel()
    val openAlertDialog = remember { mutableStateOf(false) }
    val openDeleteAccountDialog = remember {
        mutableStateOf(false)
    }
    val profileDataState = profileViewModel.profileDataState.collectAsState()
    val authStatus = profileViewModel.authStatus.collectAsState()
    val profileData by profileViewModel.profileData.collectAsState()
    val isDeleteSuccess = profileViewModel.isDeleteSuccessResponse.collectAsState()

    LaunchedEffect(key1 = true) {
        profileViewModel.getProfileData()

    }

    BackHandler {
        navController.navigate(ConveniiScreen.Home.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    when {
        openAlertDialog.value -> {
            CustomSelectDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirm = {
                    profileViewModel.deleteToken()
                    openAlertDialog.value = false
                    navController.navigate("start") {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                mainTitle = "로그아웃",
                subTitle = "로그아웃 하시겠습니까?",
                confirmBtnText = "확인",
                cancelBtnText = "취소",
            )
        }
    }

    when {
        openDeleteAccountDialog.value -> {
            CustomSelectDialog(
                onDismissRequest = { openDeleteAccountDialog.value = false },
                onConfirm = {
                    profileViewModel.deleteAccount()
                    openDeleteAccountDialog.value = false
                },
                mainTitle = "회원탈퇴",
                subTitle = "회원탈퇴 하시겠습니까?",
                confirmBtnText = "확인",
                cancelBtnText = "취소",
            )
        }
    }

    when {
        isDeleteSuccess.value -> {
            CustomConfirmDialog(
                onDismissRequest = {
                    profileViewModel.resetIsDeleteSuccess()
                    profileViewModel.deleteToken()
                    navController.navigate("start") {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                mainTitle = "회원탈퇴",
                subTitle = "회원탈퇴가 완료되었습니다",
                btnText = "확인",
            )
        }
    }

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
        bottomBar = {
            BottomNav(navController = navController)
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (profileDataState.value is APIResponse.Success) {

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
                        text = profileData.email,
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
                        modifier = Modifier
                            .height(64.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                changePwViewModel.verifyEmailSend(profileData.email)
                                navController.navigate("change2/${profileData.email}")
                            }
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
                        modifier = Modifier
                            .height(64.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) { openAlertDialog.value = true }
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
                            ),
                            modifier = Modifier.clickable {
                                openDeleteAccountDialog.value = true
                            }
                        )

                    }
                }// main colum

            } else if (profileDataState.value is APIResponse.Error && (profileDataState.value as APIResponse.Error).errorCode == "401") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Text(text = "로그인이 필요한 서비스입니다.")
                    Button(
                        onClick = {
                            navController.navigate("start") {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFE6E8EB)),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            containerColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(52.dp)
                            .padding(horizontal = 16.dp)

                    ) {

                        Text(
                            text = "로그인 하기",
                            style = TextStyle(
                                fontSize = 18.sp
                            ),
                            fontFamily = pretendard
                        )
                    }
                }
            }
        }
    }
}


