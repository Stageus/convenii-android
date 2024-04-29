package com.example.convenii.viewModel.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductData
import com.example.convenii.repository.MainRepository
import com.example.convenii.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _tmpData = MutableStateFlow("")
    val tmpData: StateFlow<String> = _tmpData

    private val _homeGSData =
        MutableStateFlow<APIResponse<ProductData.ProductCompanyResponseData>>(APIResponse.Empty())
    val homeGSData: StateFlow<APIResponse<ProductData.ProductCompanyResponseData>> = _homeGSData


    fun getHomeProductCompanyData(
        companyIdx: Int
    ) {
        viewModelScope.launch {
            _homeGSData.value = mainRepository.getProductCompany(
                companyIdx = companyIdx,
                page = 1,
                option = "main"
            )
        }

        if (_homeGSData.value is APIResponse.Error) {
            Log.d("HomeViewModel", _homeGSData.value.message.toString())
        }

        Log.d("HomeViewModel", homeGSData.value.toString())
    }


}