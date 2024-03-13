@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.convenii.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            modifier = Modifier.clickable { if (enabled) onClick() })
    }
}
