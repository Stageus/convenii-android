package com.example.convenii.viewModel.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductModel
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
        MutableStateFlow<APIResponse<ProductModel.ProductCompanyResponseData>>(APIResponse.Empty())
    val homeGSData: StateFlow<APIResponse<ProductModel.ProductCompanyResponseData>> = _homeGSData

    private val _homeCUData =
        MutableStateFlow<APIResponse<ProductModel.ProductCompanyResponseData>>(APIResponse.Empty())
    val homeCUData: StateFlow<APIResponse<ProductModel.ProductCompanyResponseData>> = _homeCUData

    private val _homeEMartData =
        MutableStateFlow<APIResponse<ProductModel.ProductCompanyResponseData>>(APIResponse.Empty())
    val homeEMartData: StateFlow<APIResponse<ProductModel.ProductCompanyResponseData>> =
        _homeEMartData

    private val _authStatus = MutableStateFlow("false")
    val authStatus: StateFlow<String> = _authStatus


    fun getHomeProductCompanyData(
        companyIdx: Int
    ) {
        viewModelScope.launch {
            when (companyIdx) {
                1 -> {
                    _homeGSData.value = mainRepository.getProductCompany(
                        companyIdx = companyIdx,
                        page = 1,
                        option = "main"
                    )
                }

                2 -> {
                    _homeCUData.value = mainRepository.getProductCompany(
                        companyIdx = companyIdx,
                        page = 1,
                        option = "main"
                    )
                }

                3 -> {
                    _homeEMartData.value = mainRepository.getProductCompany(
                        companyIdx = companyIdx,
                        page = 1,
                        option = "main"
                    )

                }
            }

            if (_homeGSData.value is APIResponse.Error) {
                Log.d("HomeViewModel", _homeGSData.value.message.toString())
            } else if (_homeGSData.value is APIResponse.Success) {
                _authStatus.value = (homeGSData.value as APIResponse.Success).data!!.authStatus
            }

            Log.d("HomeViewModel", homeGSData.value.toString())
        }
    }

    fun deleteToken() {
        viewModelScope.launch {
            tokenRepository.deleteToken()
        }
    }
}