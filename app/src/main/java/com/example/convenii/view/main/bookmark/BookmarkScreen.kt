import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.convenii.model.APIResponse
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.ConveniiScreen
import com.example.convenii.view.components.BottomNav
import com.example.convenii.view.components.MainCard
import com.example.convenii.viewModel.main.bookmark.BookmarkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navController: NavController,
) {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val bookmarkDataState = viewModel.bookmarkDataState.collectAsState()
    val lazyListState = rememberLazyListState()
    val bookmarkData = viewModel.bookmarkData.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.resetData()
        viewModel.getBookmarkData()
    }

    LaunchedEffect(lazyListState, viewModel.isDataEnded) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                if (lastVisibleItem != null && lastVisibleItem.index == bookmarkDataState.value.data!!.data.productList.size - 1 && !viewModel.isDataEnded.value) {
                    viewModel.getBookmarkData()
                }
            }

    }

    BackHandler {
        navController.navigate(ConveniiScreen.Home.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
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
                },
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "북마크", style = TextStyle(
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
        Box(modifier = Modifier.padding(innerPadding)) {
            if (bookmarkDataState.value is APIResponse.Success) {
                if (bookmarkDataState.value.data!!.authStatus == "false") {
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
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(top = 16.dp)
                            .padding(horizontal = 20.dp),
                        userScrollEnabled = true,
                        state = lazyListState
                    ) {

                        items(bookmarkData.value.size) {
                            if (it != 0) {
                                Spacer(modifier = Modifier.padding(3.dp))
                            }
                            MainCard(
                                name = bookmarkData.value[it].name,
                                price = bookmarkData.value[it].price,
                                bookmarked = bookmarkData.value[it].bookmarked,
                                events = bookmarkData.value[it].events,
                                clickEvent = {
                                    navController.navigate("productDetail/${bookmarkData.value[it].idx}")
                                },
                                productImg = bookmarkData.value[it].productImg,
                            )
                        }


                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                )
            }
        }
    }
}