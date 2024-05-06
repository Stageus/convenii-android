@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.convenii.R
import com.example.convenii.model.main.ProductModel
import com.example.convenii.ui.theme.pretendard
import com.skydoves.landscapist.glide.GlideImage

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
fun MainCard(
    name: String,
    price: String,
    bookmarked: Boolean,
    events: List<ProductModel.EventData>,
    clickEvent: () -> Unit,
    productImg: String,
) {

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
            .clickable { clickEvent() }
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

            GlideImage(
                imageModel = { productImg },
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 2.dp,
                        color = Color(0xffE6E8EB),
                        shape = RoundedCornerShape(12.dp)
                    )

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
                    text = name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "${price}원",
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
                for (company in events) {
                    val event: String = when (company.eventIdx) {
                        1 -> "1 + 1"
                        2 -> "2 + 1"
                        3 -> "할인"
                        4 -> "덤증정"
                        5 -> "기타"
                        else -> "행사 없음"
                    }
                    if (company.companyIdx == 1) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            GlideImage(
                                imageModel = { R.drawable.image_gs25 },
                                modifier = Modifier
                                    .size(25.dp)
                            )
                            Text(
                                text = event,
                                modifier = Modifier.padding(start = 4.dp),
                                style = TextStyle(fontSize = 12.sp, fontFamily = pretendard)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))

                    } else if (company.companyIdx == 2) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.End)

                        ) {
                            GlideImage(
                                imageModel = { R.drawable.image_cu },
                                modifier = Modifier
                                    .size(25.dp)
                            )
                            Text(
                                text = event,
                                modifier = Modifier.padding(start = 4.dp),
                                style = TextStyle(fontSize = 12.sp, fontFamily = pretendard)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.End)

                        ) {
                            GlideImage(
                                imageModel = { R.drawable.image_emart24 },
                                modifier = Modifier
                                    .size(25.dp)
                            )
                            Text(
                                text = event,
                                modifier = Modifier.padding(start = 4.dp),
                                style = TextStyle(fontSize = 12.sp, fontFamily = pretendard)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (bookmarked) {
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

@Composable
fun CustomConfirmDialog(
    onDismissRequest: () -> Unit,
    mainTitle: String,
    subTitle: String,
    btnText: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
            ) {
                Text(
                    text =
                    mainTitle,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subTitle,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff757779)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1F8CE6),
                    )
                ) {
                    Text(
                        text = btnText,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            } // column
        }
    }
}

@Composable
fun CustomSelectDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    mainTitle: String,
    subTitle: String,
    confirmBtnText: String,
    cancelBtnText: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
            ) {
                Text(
                    text =
                    mainTitle,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subTitle,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff757779)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .height(52.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffF4F6F8),
                        )
                    ) {
                        Text(
                            text = cancelBtnText,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))


                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .height(52.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffF83A41),
                        )
                    ) {
                        Text(
                            text = confirmBtnText,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            } // column
        }
    }
}


