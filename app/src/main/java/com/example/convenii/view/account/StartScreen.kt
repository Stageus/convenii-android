package com.example.convenii.view.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.CustomConfirmDialog
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun StartScreen(
    navController: NavController
) {

    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            CustomConfirmDialog(
                onDismissRequest = {
                    openAlertDialog.value = false
                },
                mainTitle = "준혁이바보",
                subTitle = "준혁이 매우바보",
                btnText = "확인"
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 32.dp)
        ) {
            GlideImage(
                imageModel = { R.drawable.image_main },
                modifier = Modifier
                    .size(500.dp)
            )
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                onClick = {
                    navController.navigate("SignIn")
                },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE6E8EB)),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_email),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "이메일로 로그인하기",
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    fontFamily = pretendard
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "로그인없이 시작하기",
                style = com.example.convenii.ui.theme.Typography.bodyLarge.merge(
                    fontSize = 12.sp,
                    color = Color(0xFF646F7C),
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable {
                    navController.navigate("home")
                }
            )

        }
    }

}


