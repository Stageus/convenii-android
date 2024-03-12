package com.example.convenii.view.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun StartScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Start Screen",
            modifier = Modifier.align(Alignment.Center)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
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
            )

        }
    }

}


