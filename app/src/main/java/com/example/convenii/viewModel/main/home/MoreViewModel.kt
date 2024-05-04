package com.example.convenii.viewModel.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductModel
import com.example.convenii.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoreViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _page = MutableStateFlow(1)
    val page: StateFlow<Int> = _page
    private val _moreDataState =
        MutableStateFlow<APIResponse<ProductModel.ProductCompanyResponseData>>(APIResponse.Empty())
    val moreDataState: StateFlow<APIResponse<ProductModel.ProductCompanyResponseData>> =
        _moreDataState

    private val _moreData = MutableStateFlow(mutableListOf<ProductModel.ProductCompanyData>())
    val moreData: StateFlow<MutableList<ProductModel.ProductCompanyData>> = _moreData

    private val _isDataEnded = MutableStateFlow(false)
    val isDataEnded: StateFlow<Boolean> = _isDataEnded


    init {
        resetData()
    }

    private fun resetData() {
        moreData.value.clear()
        _page.value = 1
    }

    fun getProductCompanyData(
        companyIdx: Int,
    ) {
        viewModelScope.launch {
            _moreDataState.value = mainRepository.getProductCompany(
                companyIdx = companyIdx,
                page = page.value,
                option = "all"
            )

            if (_moreDataState.value is APIResponse.Success) {
                val newData = (_moreDataState.value as APIResponse.Success).data!!.data.productList
                if (newData.isEmpty()) {
                    _isDataEnded.value = true
                    return@launch
                }
                _moreData.value = ArrayList(_moreData.value).apply {
                    addAll(newData)
                }

                _page.value++
            } else {
                _isDataEnded.value = true
            }
        }

    }


}