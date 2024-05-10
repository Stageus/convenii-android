package com.example.convenii.viewModel.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.detail.ProductDetailModel
import com.example.convenii.model.product.ProductAddModel
import com.example.convenii.repository.DetailRepository
import com.example.convenii.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val _cuSelectedEvent = MutableStateFlow("행사없음")
    val cuSelectedEvent: StateFlow<String> = _cuSelectedEvent

    private val _gsSelectedEvent = MutableStateFlow("행사없음")
    val gsSelectedEvent: StateFlow<String> = _gsSelectedEvent

    private val _emartSelectedEvent = MutableStateFlow("행사없음")
    val emartSelectedEvent: StateFlow<String> = _emartSelectedEvent

    private val _categorySelectedEvent = MutableStateFlow("음료")
    val categorySelectedEvent: StateFlow<String> = _categorySelectedEvent

    private val _cuEventPrice = MutableStateFlow("")
    val cuEventPrice: StateFlow<String> = _cuEventPrice
    private val _gsEventPrice = MutableStateFlow("")
    val gsEventPrice: StateFlow<String> = _gsEventPrice
    private val _emartEventPrice = MutableStateFlow("")
    val emartEventPrice: StateFlow<String> = _emartEventPrice

    private val _selectedImageMultipart = MutableStateFlow<MultipartBody.Part?>(null)
    val selectedImageMultipart: StateFlow<MultipartBody.Part?> = _selectedImageMultipart

    private val _isAddEnabled = MutableStateFlow(false)
    val isAddEnabled: StateFlow<Boolean> = _isAddEnabled

    private val _isEditEnabled = MutableStateFlow(true)
    val isEditEnabled: StateFlow<Boolean> = _isEditEnabled

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName

    private val _productPrice = MutableStateFlow("")
    val productPrice: StateFlow<String> = _productPrice

    private val _isUploadSuccess = MutableStateFlow(false)
    val isUploadSuccess: StateFlow<Boolean> = _isUploadSuccess

    private val _addProductState =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())
    val addProductState: StateFlow<APIResponse<CommonResponseData.Response>> = _addProductState

    private val _productDetailDataState =
        MutableStateFlow<APIResponse<ProductDetailModel.ProductDetailResponseData>>(APIResponse.Empty())
    val productDetailDataState: StateFlow<APIResponse<ProductDetailModel.ProductDetailResponseData>> =
        _productDetailDataState

    private val _networkImg = MutableStateFlow<String>("")
    val networkImg: StateFlow<String> = _networkImg

    val _errorCode = MutableStateFlow<String>("")
    val errorCode: StateFlow<String> = _errorCode

    fun setSelectedEvent(company: String, event: String) {
        when (company) {
            "CU" -> {
                _cuSelectedEvent.value = event
            }

            "GS25" -> {
                _gsSelectedEvent.value = event
            }

            "Emart24" -> {
                _emartSelectedEvent.value = event
            }
        }
    }

    fun setEventPrice(company: String, price: String) {
        when (company) {
            "CU" -> {
                Log.d("ProductViewModel", "setEventPrice: $price")
                _cuEventPrice.value = price
            }

            "GS25" -> {
                _gsEventPrice.value = price
            }

            "Emart24" -> {
                _emartEventPrice.value = price
            }
        }
    }

    fun setCategory(category: String) {
        _categorySelectedEvent.value = category
    }

    fun setImageMultipart(multipartBody: MultipartBody.Part?) {
        _selectedImageMultipart.value = multipartBody
        checkAddEnabled()
    }

    fun setProductName(name: String) {
        _productName.value = name
    }

    fun setProductPrice(price: String) {
        _productPrice.value = price
    }

    fun deleteImageMultipart() {
        _selectedImageMultipart.value = null
        checkAddEnabled()
    }

    fun checkAddEnabled() {
        if (_selectedImageMultipart.value != null && _productName.value.isNotEmpty() && _productPrice.value.isNotEmpty()) {
            _isAddEnabled.value = true
        } else {
            _isAddEnabled.value = false
        }
        if (_productName.value.isNotEmpty() && _productPrice.value.isNotEmpty()) {
            _isEditEnabled.value = true
        } else {
            _isEditEnabled.value = false
        }
    }


    fun addProduct() {
        _addProductState.value = APIResponse.Loading()
        val eventInfo: MutableList<ProductAddModel.EventInfoData> = mutableListOf()
        if (_cuSelectedEvent.value != "제품없음") {
            eventInfo.add(
                ProductAddModel.EventInfoData(
                    2,
                    eventToInt(_cuSelectedEvent.value),
                    if (_cuEventPrice.value.isEmpty()) null else _cuEventPrice.value.toInt()
                )
            )
        }
        if (_gsSelectedEvent.value != "제품없음") {
            eventInfo.add(
                ProductAddModel.EventInfoData(
                    1,
                    eventToInt(_gsSelectedEvent.value),
                    if (_gsEventPrice.value.isEmpty()) null else _gsEventPrice.value.toInt()
                )
            )
        }
        if (_emartSelectedEvent.value != "제품없음") {
            eventInfo.add(
                ProductAddModel.EventInfoData(
                    3,
                    eventToInt(_emartSelectedEvent.value),
                    if (_emartEventPrice.value.isEmpty()) null else _emartEventPrice.value.toInt()
                )
            )
        }

        val body = ProductAddModel.ProductAddRequestBody(
            categoryIdx = categoryToInt(),
            _productName.value,
            _productPrice.value,
            eventInfo
        )

        viewModelScope.launch {
            Log.d("productPrice", _productName.value)
            _addProductState.value = productRepository.addProduct(
                categoryIdx = body.categoryIdx,
                name = body.name,
                price = body.price,
                image = _selectedImageMultipart.value!!,
                eventInfo = body.eventInfo
            )
            if (_addProductState.value is APIResponse.Success) {
                _isUploadSuccess.value = true

            } else {
                _errorCode.value = (_addProductState.value as APIResponse.Error).errorCode!!
                Log.d(
                    "ProductViewModel",
                    "addProduct: ${_addProductState.value.message} ${_addProductState.value.errorCode}"
                )
            }

        }
    }

    private fun categoryToInt(): Int {
        return when (_categorySelectedEvent.value) {
            "음료" -> 1
            "과자" -> 2
            "식품" -> 3
            "아이스크림" -> 4
            "생활용품" -> 5
            "기타" -> 6
            else -> 0
        }
    }

    private fun eventToInt(event: String): Int {
        return when (event) {
            "1 + 1" -> 1
            "1 + 2" -> 2
            "할인" -> 3
            "덤증정" -> 4
            "기타" -> 5
            "행사없음" -> 6
            else -> 0
        }
    }

    private fun eventToString(event: Int): String {
        return when (event) {
            1 -> "1 + 1"
            2 -> "1 + 2"
            3 -> "할인"
            4 -> "덤증정"
            5 -> "기타"
            6 -> "행사없음"
            else -> ""
        }
    }

    private fun categoryToString(category: Int): String {
        return when (category) {
            1 -> "음료"
            2 -> "과자"
            3 -> "식품"
            4 -> "아이스크림"
            5 -> "생활용품"
            6 -> "기타"
            else -> ""
        }
    }

    fun resetUploadSuccess() {
        _isUploadSuccess.value = false
    }

    fun getDetailData(productIdx: Int) {
        viewModelScope.launch {
            _productDetailDataState.value = detailRepository.getDetailProduct(productIdx)
            if (_productDetailDataState.value is APIResponse.Success) {
                val newData =
                    (_productDetailDataState.value as APIResponse.Success).data!!.data.product

                _cuSelectedEvent.value =
                    newData.eventInfo[0].events.find { it.companyIdx == 1 }?.let {
                        eventToString(it.eventIdx)
                    } ?: "행사없음"

                _gsSelectedEvent.value =
                    newData.eventInfo[0].events.find { it.companyIdx == 2 }?.let {
                        eventToString(it.eventIdx)
                    } ?: "행사없음"

                _emartSelectedEvent.value =
                    newData.eventInfo[0].events.find { it.companyIdx == 3 }?.let {
                        eventToString(it.eventIdx)
                    } ?: "행사없음"

                _categorySelectedEvent.value = categoryToString(newData.categoryIdx)

                _networkImg.value = newData.productImg

                _productName.value = newData.name
                _productPrice.value = newData.price

            }
        }
    }

    fun editProduct(productIdx: Int) {
        val eventInfo: MutableList<ProductAddModel.EventInfoData> = mutableListOf()
        if (_cuSelectedEvent.value != "제품없음") {
            eventInfo.add(
                ProductAddModel.EventInfoData(
                    2,
                    eventToInt(_cuSelectedEvent.value),
                    if (_cuEventPrice.value.isEmpty()) null else _cuEventPrice.value.toInt()
                )
            )
        }
        if (_gsSelectedEvent.value != "제품없음") {
            eventInfo.add(
                ProductAddModel.EventInfoData(
                    1,
                    eventToInt(_gsSelectedEvent.value),
                    if (_gsEventPrice.value.isEmpty()) null else _gsEventPrice.value.toInt()
                )
            )
        }
        if (_emartSelectedEvent.value != "제품없음") {
            eventInfo.add(
                ProductAddModel.EventInfoData(
                    3,
                    eventToInt(_emartSelectedEvent.value),
                    if (_emartEventPrice.value.isEmpty()) null else _emartEventPrice.value.toInt()
                )
            )
        }

        val body = ProductAddModel.ProductAddRequestBody(
            categoryIdx = categoryToInt(),
            _productName.value,
            _productPrice.value,
            eventInfo
        )
        viewModelScope.launch {
            _addProductState.value = productRepository.editProduct(
                productIdx = productIdx,
                categoryIdx = body.categoryIdx,
                name = body.name,
                price = body.price,
                image = _selectedImageMultipart.value,
                eventInfo = body.eventInfo
            )
            if (_addProductState.value is APIResponse.Success) {
                _isUploadSuccess.value = true
                Log.d("ProductViewModel", "editProduct: ${_addProductState.value.message}")

            } else {
                _errorCode.value = (_addProductState.value as APIResponse.Error).errorCode!!
                Log.d(
                    "ProductViewModel",
                    "addProduct: ${_addProductState.value.message} ${_addProductState.value.errorCode}"
                )
            }

        }
    }

    fun resetErrorCode() {
        _errorCode.value = ""
    }

}

