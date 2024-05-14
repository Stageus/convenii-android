package com.example.convenii.viewModel.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.detail.ProductDetailModel
import com.example.convenii.model.detail.ReviewModel
import com.example.convenii.repository.BookmarkRepository
import com.example.convenii.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _productDetailDataState =
        MutableStateFlow<APIResponse<ProductDetailModel.ProductDetailResponseData>>(APIResponse.Empty())
    val productDetailDataState: StateFlow<APIResponse<ProductDetailModel.ProductDetailResponseData>> =
        _productDetailDataState

    private val _productReviewState =
        MutableStateFlow<APIResponse<ReviewModel.GetReviewResponseData>>(APIResponse.Empty())
    val productReviewState: StateFlow<APIResponse<ReviewModel.GetReviewResponseData>> =
        _productReviewState

    private val _reviewData = MutableStateFlow(mutableListOf<ReviewModel.ReviewData>())
    val reviewData: StateFlow<MutableList<ReviewModel.ReviewData>> = _reviewData

    private var page = 1

    private val _reviewResponse =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())
    val reviewResponse: StateFlow<APIResponse<CommonResponseData.Response>> = _reviewResponse

    private val _reviewCompleteState = MutableStateFlow(false)
    val reviewCompleteState: StateFlow<Boolean> = _reviewCompleteState

    private val _isReviewDataEnded = MutableStateFlow(false)
    val isReviewDataEnded: StateFlow<Boolean> = _isReviewDataEnded

    private val _isDataLoading = MutableStateFlow(false)
    val isDataLoading: StateFlow<Boolean> = _isDataLoading

    private val _isBookmark = MutableStateFlow(false)
    val isBookmark: StateFlow<Boolean> = _isBookmark

    private val _productDetailState =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())

    private val _isProductDeleted = MutableStateFlow(false)
    val isProductDeleted: StateFlow<Boolean> = _isProductDeleted


    fun getProductDetailData(
        productIdx: Int
    ) {
        viewModelScope.launch {
            _productDetailDataState.value = detailRepository.getDetailProduct(productIdx)
            if (_productDetailDataState.value is APIResponse.Success) {

                val newData =
                    (_productDetailDataState.value as APIResponse.Success).data!!.data.product
                Log.d("DetailViewModel", "getProductDetailData: ${newData}")
                _isBookmark.value = newData.bookmarked
            }

        }
    }

    fun convertEventData(eventInfoData: List<ProductDetailModel.EventInfoData>): List<List<String>> {
        val tableData = mutableListOf(
            listOf("", "GS25", "CU", "EMart24"),
        )
        for (data in eventInfoData) {
            val month = data.month.substring(data.month.length - 2) + "월"
            val rowData = mutableListOf(month)

            // 각 회사별로 이벤트 처리
            val companies = listOf(1, 2, 3) // GS25, CU, Emart24의 companyIdx
            companies.forEach { companyIdx ->
                val event = data.events.find { it.companyIdx == companyIdx }
                val eventDescription = when (event?.eventIdx) {
                    1 -> "1+1"
                    2 -> "2+1"
                    3 -> "할인 ${event.price}"
                    4 -> "덤증정"
                    5 -> "기타"
                    6 -> "행사없음"
                    else -> ""
                }
                rowData.add(eventDescription)
            }

            tableData.add(rowData)
        }
        return tableData
    }

    fun getProductReviewMain(
        productIdx: Int,
    ) {
        viewModelScope.launch {
            _productReviewState.value = detailRepository.getProductReview(productIdx, 1)

            if (_productReviewState.value is APIResponse.Success) {
                val newData = (_productReviewState.value as APIResponse.Success).data!!.data.reviews
                newData.forEach {
                    it.createdAt = dateProcessing(it.createdAt)
                }
                _reviewData.value = newData.toMutableList()
            }
        }
    }

    fun getProductReviewDetail(
        productIdx: Int
    ) {
        _isDataLoading.value = true
        viewModelScope.launch {
            _productReviewState.value = detailRepository.getProductReview(productIdx, page)
            if (_productReviewState.value is APIResponse.Success) {
                val newData = (_productReviewState.value as APIResponse.Success).data!!.data.reviews
                if (newData.isEmpty()) {
                    _isReviewDataEnded.value = true
                    return@launch
                }

                newData.forEach {
                    it.createdAt = dateProcessing(it.createdAt)
                }
                _reviewData.value = ArrayList(_reviewData.value).apply {
                    addAll(newData)
                }
            } else {
                _isReviewDataEnded.value = true

            }
        }
        page++

        _isDataLoading.value = false
    }

    fun postProductReview(
        productIdx: Int,
        score: Int,
        content: String
    ) {
        val body = ReviewModel.PostReviewRequestData(score, content)
        _reviewResponse.value = APIResponse.Loading()
        viewModelScope.launch {
            _reviewResponse.value = detailRepository.postProductReview(productIdx, body)

            if (_reviewResponse.value is APIResponse.Success) {
                _reviewCompleteState.value = true
            } else {
                Log.d(
                    "DetailViewModel",
                    "postProductReview: ${(_reviewResponse.value as APIResponse.Error).errorCode}"
                )
            }
        }
    }

    fun resetReviewCompleteState() {
        _reviewCompleteState.value = false
    }

    private fun dateProcessing(dateStr: String): String {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val givenDate =
            ZonedDateTime.parse(dateStr, formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul"))
        val currentDate = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        //currentDate 형식 변환


        val minutesDiff = ChronoUnit.MINUTES.between(givenDate, currentDate)
        val hoursDiff = ChronoUnit.HOURS.between(givenDate, currentDate)
        val daysDiff = ChronoUnit.DAYS.between(givenDate, currentDate)

        return when {
            minutesDiff < 1 -> "방금 전"
            minutesDiff < 60 -> "${minutesDiff}분 전"
            hoursDiff < 24 -> "${hoursDiff}시간 전"
            else -> "${daysDiff}일 전"
        }
    }

    fun postBookmark(productIdx: Int) {
        viewModelScope.launch {
            val response = bookmarkRepository.postBookmark(productIdx)
            if (response is APIResponse.Success) {
                _isBookmark.value = true
            }
        }
    }

    fun deleteBookmark(productIdx: Int) {
        viewModelScope.launch {
            val response = bookmarkRepository.deleteBookmark(productIdx)
            if (response is APIResponse.Success) {
                _isBookmark.value = false

            }
        }
    }

    fun deleteProduct(productIdx: Int) {
        viewModelScope.launch {
            _productDetailState.value = detailRepository.deleteProduct(productIdx)

            if (_productDetailState.value is APIResponse.Success) {
                Log.d("DetailViewModel", "deleteProduct: success")
                _isProductDeleted.value = true
            } else {
                Log.d(
                    "DetailViewModel",
                    "deleteProduct: ${(_productDetailState.value as APIResponse.Error).message}"
                )
            }
        }

    }

    fun resetProductDeleted() {
        _isProductDeleted.value = false
    }

}