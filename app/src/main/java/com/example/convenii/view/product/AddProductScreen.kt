package com.example.convenii.view.product

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.model.APIResponse
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.AccountInputField
import com.example.convenii.view.components.ConfirmBtn
import com.example.convenii.view.components.CustomConfirmDialog
import com.example.convenii.viewModel.product.ProductViewModel
import com.skydoves.landscapist.glide.GlideImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

@Composable
fun rememberImagePickerLauncher(onImagePicked: (Uri?) -> Unit): ActivityResultLauncher<String> {
    return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onImagePicked(uri)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavController
) {
    val viewModel: ProductViewModel = hiltViewModel()
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density

    val addProductState by viewModel.addProductState.collectAsState()

    var categoryExpandStatus by remember { mutableStateOf(false) }
    var cuExpandStatus by remember { mutableStateOf(false) }
    var gsExpandStatus by remember { mutableStateOf(false) }
    var emartExpandStatus by remember { mutableStateOf(false) }

    val categorySelectedEvent = viewModel.categorySelectedEvent.collectAsState()
    val cuSelectedEvent = viewModel.cuSelectedEvent.collectAsState()
    val gsSelectedEvent = viewModel.gsSelectedEvent.collectAsState()
    val emartSelectedEvent = viewModel.emartSelectedEvent.collectAsState()

    val eventList = arrayOf("제품없음", "행사없음", "1 + 1", "1 + 2", "덤증정", "기타", "할인")
    val categoryList = arrayOf("음료", "과자", "식품", "아이스크림", "생활용품", "기타")

    val productName by viewModel.productName.collectAsState()
    val productPrice by viewModel.productPrice.collectAsState()

    val cuEventPrice by viewModel.cuEventPrice.collectAsState()
    val gsEventPrice by viewModel.gsEventPrice.collectAsState()
    val emartEventPrice by viewModel.emartEventPrice.collectAsState()

    val scrollState = rememberScrollState()

    val isAddEnabled by viewModel.isAddEnabled.collectAsState()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val imagePickerLauncher = rememberImagePickerLauncher { uri ->
        val multipart = uri?.let { convertUriToMultipart(context, it) }
        selectedImageUri = uri
        viewModel.setImageMultipart(multipart)
    }

    val isUploadSuccess by viewModel.isUploadSuccess.collectAsState()

    val errorCode by viewModel._errorCode.collectAsState()

    when {
        errorCode == "413" ->
            CustomConfirmDialog(
                onDismissRequest = {
                    viewModel.resetErrorCode()
                },
                mainTitle = "상품추가",
                subTitle = "5mb 이하의 이미지만 업로드 가능합니다",
                btnText = "확인",
            )
    }

    when {
        isUploadSuccess -> {
            CustomConfirmDialog(
                onDismissRequest = {
                    viewModel.resetUploadSuccess()
                    navController.popBackStack()
                },
                mainTitle = "상품 추가",
                subTitle = "상품이 추가되었습니다",
                btnText = "확인",
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_back),
                                contentDescription = null,
                            )

                        }
                    },
                    title = { Text(text = "") }
                )

            },
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {

                    if (selectedImageUri == null) {
                        GlideImage(
                            imageModel = {
                                R.drawable.image_add_box
                            },
                            modifier = Modifier
                                .size(screenWidth / 2)
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    imagePickerLauncher.launch("image/*")
                                }
                        )
                    } else {
                        GlideImage(
                            imageModel = { selectedImageUri },
                            modifier = Modifier
                                .size(screenWidth / 2)
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    viewModel.deleteImageMultipart()
                                    selectedImageUri = null
                                }
                                .clip(RoundedCornerShape(10.dp))

                        )
                    }

                    Spacer(modifier = Modifier.size(37.dp))

                    Text(
                        text = "상품이름",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    AccountInputField(
                        keyboardOptions = KeyboardOptions.Default,
                        isPassword = false,
                        text = productName,
                        valueChange = {
                            viewModel.setProductName(it)
                            viewModel.checkAddEnabled()
                        },
                        placeholder = "상품이름을 입력해주세요",
                        isError = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )

                    Spacer(modifier = Modifier.size(25.dp))

                    Text(
                        text = "상품가격",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    AccountInputField(
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        isPassword = false,
                        text = productPrice,
                        valueChange = {
                            viewModel.setProductPrice(it)
                            viewModel.checkAddEnabled()
                        },
                        placeholder = "상품가격을 입력해주세요",
                        isError = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )

                    Spacer(modifier = Modifier.size(25.dp))

                    Text(
                        text = "카테고리",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.size(14.dp))

                    ExposedDropdownMenuBox(
                        expanded = categoryExpandStatus,
                        onExpandedChange = { categoryExpandStatus = it },
                        modifier = Modifier
                            .width(screenWidth)
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .menuAnchor()
                                .height(40.dp)
                                .fillMaxWidth()
                                .border(1.dp, Color(0xffD1D1D1), RoundedCornerShape(8.dp))
                                .padding(top = 10.dp),
                            singleLine = true,

                            textStyle = TextStyle.Default.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                fontFamily = pretendard

                            ),
                            readOnly = true,
                            value = categorySelectedEvent.value,
                            onValueChange = {},
                        )
                        ExposedDropdownMenu(
                            expanded = categoryExpandStatus,
                            onDismissRequest = { categoryExpandStatus = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            categoryList.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            selectionOption,
                                            fontFamily = pretendard,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    onClick = {
                                        viewModel.setCategory(selectionOption)
                                        categoryExpandStatus = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }


                    Text(
                        text = "행사목록",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.size(14.dp))

                    SelectEventRow(
                        company = "CU",
                        expandStatus = cuExpandStatus,
                        onExpandedChange = { cuExpandStatus = it },
                        screenWidth = screenWidth,
                        selectedEvent = cuSelectedEvent,
                        onDismissRequest = { cuExpandStatus = false },
                        eventList = eventList,
                        viewModel = viewModel
                    )

                    if (cuSelectedEvent.value == "할인") {
                        Spacer(modifier = Modifier.size(7.dp))
                        EventPriceRow(
                            company = "CU",
                            screenWidth = screenWidth,
                            eventPrice = cuEventPrice,
                            viewModel = viewModel
                        )
                    }

                    Spacer(modifier = Modifier.size(14.dp))
                    SelectEventRow(
                        company = "GS25",
                        expandStatus = gsExpandStatus,
                        onExpandedChange = { gsExpandStatus = it },
                        screenWidth = screenWidth,
                        selectedEvent = gsSelectedEvent,
                        onDismissRequest = { gsExpandStatus = false },
                        eventList = eventList,
                        viewModel = viewModel
                    )
                    if (gsSelectedEvent.value == "할인") {
                        Spacer(modifier = Modifier.size(7.dp))
                        EventPriceRow(
                            company = "GS25",
                            screenWidth = screenWidth,
                            eventPrice = gsEventPrice,
                            viewModel = viewModel
                        )
                    }

                    Spacer(modifier = Modifier.size(14.dp))
                    SelectEventRow(
                        company = "Emart24",
                        expandStatus = emartExpandStatus,
                        onExpandedChange = { emartExpandStatus = it },
                        screenWidth = screenWidth,
                        selectedEvent = emartSelectedEvent,
                        onDismissRequest = { emartExpandStatus = false },
                        eventList = eventList,
                        viewModel = viewModel
                    )
                    if (emartSelectedEvent.value == "할인") {
                        Spacer(modifier = Modifier.size(7.dp))
                        EventPriceRow(
                            company = "Emart24",
                            screenWidth = screenWidth,
                            eventPrice = emartEventPrice,
                            viewModel = viewModel
                        )
                    }
                    Spacer(modifier = Modifier.size(14.dp))
                    ConfirmBtn(
                        text = "상품 추가하기",
                        enabled = isAddEnabled,
                        onClick = {
                            viewModel.addProduct()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(14.dp))
                }


            }
        }

        if (addProductState is APIResponse.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(60.dp),
                    color = Color.Black,
                    trackColor = Color.White,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectEventRow(
    company: String,
    expandStatus: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    screenWidth: Dp,
    selectedEvent: State<String>,
    onDismissRequest: () -> Unit,
    eventList: Array<String>,
    viewModel: ProductViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = company, style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        // Dropdown menu for CU
        ExposedDropdownMenuBox(
            expanded = expandStatus,
            onExpandedChange = onExpandedChange,
            modifier = Modifier
                .width(screenWidth * 0.7f)
        ) {
            BasicTextField(
                modifier = Modifier
                    .menuAnchor()
                    .height(30.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color(0xffD1D1D1), RoundedCornerShape(8.dp))
                    .padding(top = 6.dp),
                singleLine = true,

                textStyle = TextStyle.Default.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard

                ),
                readOnly = true,
                value = selectedEvent.value,
                onValueChange = {},
            )
            ExposedDropdownMenu(
                expanded = expandStatus,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.background(Color.White)
            ) {
                eventList.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                selectionOption,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {
                            viewModel.setSelectedEvent(company, selectionOption)
                            onDismissRequest()
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Composable
fun EventPriceRow(
    company: String,
    screenWidth: Dp,
    eventPrice: String,
    viewModel: ProductViewModel
) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        BasicTextField(
            modifier = Modifier
                .height(30.dp)
                .width(screenWidth * 0.7f)
                .border(1.dp, Color(0xffD1D1D1), RoundedCornerShape(8.dp))
                .padding(top = 6.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontFamily = pretendard
            ),
            value = eventPrice,
            onValueChange = { if (it.length <= 7) viewModel.setEventPrice(company, it) },
        )
    }
}


fun convertUriToMultipart(context: Context, uri: Uri): MultipartBody.Part? {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri) ?: "image/*"
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val byteArray = inputStream?.readBytes()
    inputStream?.close()

    val requestBody = byteArray?.toRequestBody(mimeType.toMediaTypeOrNull(), 0, byteArray.size)
    val fileName = uri.lastPathSegment ?: "image"

    return requestBody?.let {
        MultipartBody.Part.createFormData("image", fileName, it)
    }
}



