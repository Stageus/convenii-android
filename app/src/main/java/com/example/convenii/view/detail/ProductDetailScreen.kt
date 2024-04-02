package com.example.convenii.view.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.ui.theme.pretendard

@Composable
fun ProductDetailScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = null,

                    )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .clickable(enabled = true, onClick = {})
                    .background(Color.White)
                    .height(30.dp)
                    .width(77.dp)
                    .border(
                        BorderStroke(1.dp, Color(0xffE6E8EB)),
                        shape = RoundedCornerShape(9.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "상품 삭제",
                    color = Color(0xff646F7C),
                    fontSize = 11.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .clickable(enabled = true, onClick = {})
                    .background(Color.White)
                    .height(30.dp)
                    .width(77.dp)
                    .border(
                        BorderStroke(1.dp, Color(0xffE6E8EB)),
                        shape = RoundedCornerShape(9.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "상품 수정",
                    color = Color(0xff646F7C),
                    fontSize = 11.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.test), contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth(0.41f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "준혁이의 고기쇼",
                    fontSize = 18.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.icon_fill_star),
                    contentDescription = null,
                    tint = Color(0xffFFD643)
                )
                Text(
                    text = "1 .5",
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = "3500원",
                fontSize = 16.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                EventRow(icon = R.drawable.test, event = "1 + 1")
                EventRow(icon = R.drawable.test, event = "1 + 1")
                EventRow(icon = R.drawable.test, event = "1 + 1")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "할인 히스토리",
                fontSize = 18.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            val sampleData = listOf(
                listOf("", "GS25", "CU", "Emart24"),
                listOf("1월", "Cell 2-2", "Cell 2-3", "Cell 2-4"),
                listOf("2월", "Cell 3-2", "Cell 3-3", "Cell 3-4"),
                listOf("3월", "Cell 4-2", "Cell 4-3", "Cell 4-4"),
                listOf("4월", "Cell 5-2", "Cell 5-3", "Cell 5-4"),
                listOf("5월", "Cell 6-2", "Cell 6-3", "Cell 6-4"),
                listOf("6월", "Cell 7-2", "Cell 7-3", "Cell 7-4"),
                listOf("7월", "Cell 8-2", "Cell 8-3", "Cell 8-4"),
                listOf("8월", "Cell 9-2", "Cell 9-3", "Cell 9-4"),
                listOf("9월", "Cell 10-2", "Cell 10-3", "Cell 10-4"),
                listOf("10월", "Cell 11-2", "Cell 11-3", "Cell 11-4"),
                listOf("11월", "Cell 12-2", "Cell 12-3", "Cell 12-4"),
                listOf("12월", "Cell 13-2", "Cell 13-3", "Cell 13-4"),
            )

            StaticDataTable(data = sampleData)

        }

    }
}

@Composable
fun EventRow(icon: Int, event: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = null,
            modifier = Modifier
                .width(25.dp)
                .height(25.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = event,
            modifier = Modifier.padding(start = 4.dp),
            fontSize = 12.sp,
            fontFamily = pretendard
        )
    }
}

@Composable
fun StaticDataTable(data: List<List<String>>) {
    Column(
        modifier = Modifier
    ) {
        data.forEach { rowData ->
            TableRow(rowData = rowData)
        }
    }
}

@Composable
fun TableRow(rowData: List<String>) {
    Row(
    ) {
        // 첫 번째 셀에 대한 처리
        TableCell(text = rowData[0], weight = 1.4f) // 첫 번째 열이 좁게 설정됩니다.

        // 나머지 셀들에 대한 처리
        rowData.drop(1).forEach { cellData ->
            TableCell(text = cellData, weight = 2f) // 나머지 열들은 더 넓게 설정됩니다.
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(weight)
            .height(40.dp)
            .border(1.dp, Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontFamily = pretendard,
            textAlign = TextAlign.Center

        )
    }
}
