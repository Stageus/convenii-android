package com.example.convenii.viewModel.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.profile.ProfileModel
import com.example.convenii.repository.AccountRepository
import com.example.convenii.repository.ProfileRepository
import com.example.convenii.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val profileRepository: ProfileRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _profileDataState =
        MutableStateFlow<APIResponse<ProfileModel.ProfileResponseData>>(APIResponse.Empty())
    val profileDataState: MutableStateFlow<APIResponse<ProfileModel.ProfileResponseData>> =
        _profileDataState
    private val _authStatus = MutableStateFlow("false")
    val authStatus: MutableStateFlow<String> = _authStatus
    private val _profileData =
        MutableStateFlow(ProfileModel.ProfileData(name = "", email = "", createdAt = "", idx = 0))
    val profileData: StateFlow<ProfileModel.ProfileData> = _profileData

    private val deleteAccountState =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())
    val deleteAccountResponse: StateFlow<APIResponse<CommonResponseData.Response>> =
        deleteAccountState
    private val isDeleteSuccess = MutableStateFlow(false)
    val isDeleteSuccessResponse: StateFlow<Boolean> = isDeleteSuccess

    fun getProfileData() {
        viewModelScope.launch {
            _profileDataState.value = profileRepository.getProfile()
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

    fun deleteToken() {
        viewModelScope.launch {
            tokenRepository.deleteToken()
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            deleteAccountState.value = profileRepository.deleteAccount()
            if (deleteAccountState.value is APIResponse.Success) {
                isDeleteSuccess.value = true
            } else {
                isDeleteSuccess.value = false
            }
        }
    }

    fun resetIsDeleteSuccess() {
        isDeleteSuccess.value = false
    }


}