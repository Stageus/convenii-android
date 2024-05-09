package com.example.convenii.viewModel.main.home

import android.util.Log
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

    private val _isDataLoading = MutableStateFlow(false)
    val isDataLoading: StateFlow<Boolean> = _isDataLoading


    init {
        resetData()
    }

    fun resetData() {
        moreData.value.clear()
        _isDataEnded.value = false
        _page.value = 1
    }

    fun setIsDataLoading(value: Boolean) {
        _isDataLoading.value = value
    }

    fun getProductCompanyData(
        companyIdx: Int,
    ) {
        _isDataLoading.value = true
        viewModelScope.launch {
            _moreDataState.value = mainRepository.getProductCompany(
                companyIdx = companyIdx,
                page = page.value,
                option = "all"
            )
            if (_moreDataState.value is APIResponse.Success) {
                val newData = (_moreDataState.value as APIResponse.Success).data!!.data.productList
                Log.d("MoreViewModel", "getProductCompanyData: $newData")
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
        _isDataLoading.value = false
    }


    fun setIsDataEnded(value: Boolean) {
        _isDataEnded.value = value
    }


}