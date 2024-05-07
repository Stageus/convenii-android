package com.example.convenii.viewModel.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductModel
import com.example.convenii.model.profile.ProfileModel
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

    private val _profileDataState =
        MutableStateFlow<APIResponse<ProfileModel.ProfileResponseData>>(APIResponse.Empty())
    val profileDataState: StateFlow<APIResponse<ProfileModel.ProfileResponseData>> =
        _profileDataState
    private val _profileData =
        MutableStateFlow(ProfileModel.ProfileData(name = "", email = "", createdAt = "", idx = 0))
    val profileData: StateFlow<ProfileModel.ProfileData> = _profileData


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


    //profile
    fun getProfileData() {
        viewModelScope.launch {
            _profileDataState.value = mainRepository.getProfile()
            if (_profileDataState.value is APIResponse.Success) {
                Log.d(
                    "HomeViewModel",
                    (_profileDataState.value as APIResponse.Success).data.toString()
                )
                _authStatus.value =
                    (_profileDataState.value as APIResponse.Success).data!!.authStatus
                _profileData.value = (_profileDataState.value as APIResponse.Success).data!!.data
            } else {
                Log.d(
                    "HomeViewModel",
                    "${_profileDataState.value.message} ${_profileDataState.value.errorCode}"
                )
            }
        }
    }
}