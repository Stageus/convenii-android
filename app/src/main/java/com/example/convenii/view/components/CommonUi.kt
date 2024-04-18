@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard

@Composable
fun AccountInputField(
    keyboardOptions: KeyboardOptions,
    isPassword: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    valueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean
) {
    OutlinedTextField(
        value = text,
        onValueChange = {
            valueChange(it)
        },
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = Color(0xffC5C8CE),
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium

                )
            )
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium
        ),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color(0xFFFFFFFF),
            focusedContainerColor = Color(0xFFFFFFFF),
            unfocusedContainerColor = Color(0xFFFFFFFF),
            //error에 따라 다른색상
            focusedIndicatorColor = if (isError) Color(0xffFF5A5F) else Color(0xff1F8CE6),
            unfocusedIndicatorColor = Color(0xffE6E8EB), // 포커스 없
            // 을 때 색상
        ),
    )
}

@Composable
fun ConfirmBtn(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val backgroundColor = if (enabled) Color(0xff1F8CE6) else Color(0xffEEEEEE)
    val textColor = if (enabled) Color.White else Color(0xffC5C8CE)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .background(backgroundColor)
            .height(52.dp),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 18.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MainCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,

            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
        border = BorderStroke(1.dp, Color(0xffE6E8EB)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //padding start 10%
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.04f)
            )
            Image(
                painter = painterResource(id = R.drawable.test), contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(80.dp)
                    .height(80.dp),
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.054f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.55f)

            ) {
                Text(
                    text = "핫식스sdfsfsd",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "2200원",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Spacer(Modifier.weight(1f))
            Column(
                Modifier
                    .padding(end = 20.dp, top = 20.dp)
                    .align(Alignment.Top)
                    .fillMaxWidth()

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Image(
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        painter = painterResource(id = R.drawable.test),
                        contentDescription = null
                    )
                    Text(
                        text = "1 + 1",
                        modifier = Modifier.padding(start = 4.dp),
                        style = TextStyle(fontSize = 12.sp, fontFamily = pretendard)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.End)

                ) {
                    Image(
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        painter = painterResource(id = R.drawable.test),
                        contentDescription = null
                    )
                    Text(
                        text = "1 + 1",
                        modifier = Modifier.padding(start = 4.dp),
                        style = TextStyle(fontSize = 12.sp, fontFamily = pretendard)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.End)

                ) {
                    Image(
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        painter = painterResource(id = R.drawable.test),
                        contentDescription = null
                    )
                    Text(
                        text = "1 + 1",
                        modifier = Modifier.padding(start = 4.dp),
                        style = TextStyle(fontSize = 12.sp, fontFamily = pretendard)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.align(Alignment.End)


                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_bookmark),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CommentUi(nickname: String, star: Int, comment: String, date: String) {
    Column {
        Text(
            text = nickname,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..star) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_fill_star),
                    contentDescription = null,
                    tint = Color(0xffFFD643),
                    modifier = Modifier.height(12.dp)
                )
            }

            for (i in 1..(5 - star)) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_empty_star),
                    contentDescription = null,
                    tint = Color(0xffFFD643),
                    modifier = Modifier.height(12.dp)

                )
            }

            Text(
                text = date,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff7D8791)
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Text(
            text = comment, style = TextStyle(
                fontSize = 12.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                color = Color(0xff7D8791)
            )
        )

    }
}

@Preview
@Composable
fun PreviewTest() {
//    ConfirmBtn(text = , enabled = , onClick = { /*TODO*/ }, modifier = )
}

