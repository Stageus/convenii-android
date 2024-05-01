package com.example.convenii.viewModel.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.detail.ProductDetailModel
import com.example.convenii.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val _productDetailModelState =
        MutableStateFlow<APIResponse<ProductDetailModel.ProductDetailResponseData>>(APIResponse.Empty())
    val productDetailModelState: StateFlow<APIResponse<ProductDetailModel.ProductDetailResponseData>> =
        _productDetailModelState


    fun getProductDetailData(
        productIdx: Int
    ) {
        viewModelScope.launch {
            _productDetailModelState.value = detailRepository.getDetailProduct(productIdx)

            if (_productDetailModelState.value is APIResponse.Success) {
                val newData = (_productDetailModelState.value as APIResponse.Success).data!!.data
//                _productDetailData.value =
//                    (_productDetailDataState.value as APIResponse.Success).data!!.data.product

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
                val event = data.events?.find { it.companyIdx == companyIdx }
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
}