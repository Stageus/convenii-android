package com.example.convenii.viewModel.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.repository.AccountRepository
import com.example.convenii.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChangePwViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _isItEmail = MutableStateFlow<Boolean>(false)
    val isItEmail: StateFlow<Boolean> get() = _isItEmail

    private val _verifyEmailSendState =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())
    val verifyEmailSendState: StateFlow<APIResponse<CommonResponseData.Response>> get() = _verifyEmailSendState
    private val _verifyEmailSendErrorCode = MutableStateFlow("")
    val verifyEmailSendErrorCode: StateFlow<String> get() = _verifyEmailSendErrorCode

    private val _verifyCodeCheckState =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())
    val verifyCodeCheckState: StateFlow<APIResponse<CommonResponseData.Response>> get() = _verifyCodeCheckState
    private val _verifyCodeCheckErrorCode = MutableStateFlow("")
    val verifyCodeCheckErrorCode: StateFlow<String> get() = _verifyCodeCheckErrorCode

    private val _isValidPw = MutableStateFlow<Boolean>(false)
    val isValidPw: StateFlow<Boolean> get() = _isValidPw

    private val _changePwState =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())
    val changePwState: StateFlow<APIResponse<CommonResponseData.Response>> get() = _changePwState


    fun checkIsItEmail(email: String) {
        // 이메일 형식인지 확인
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (email.matches(emailPattern.toRegex())) {
            _isItEmail.value = true
        } else {
            _isItEmail.value = false
        }
    }

    fun verifyEmailSend(email: String) {
        viewModelScope.launch {
            _verifyEmailSendState.value = accountRepository.changePwVerifyEmailSend(email)
            if (_verifyEmailSendState.value is APIResponse.Error) {
                _verifyEmailSendErrorCode.value =
                    (_verifyEmailSendState.value as APIResponse.Error).errorCode!!
                Log.d(
                    "ChangePwViewModel", "verifyEmailSend:" +
                            " ${(_verifyEmailSendState.value as APIResponse.Error).message} " +
                            "${(_verifyEmailSendState.value as APIResponse.Error).errorCode}"
                )
            }
        }
    }

    fun verifyCodeCheck(email: String, code: String) {
        viewModelScope.launch {
            _verifyCodeCheckState.value = accountRepository.verifyEmailCheck(email, code)

            if (_verifyCodeCheckState.value is APIResponse.Error) {
                _verifyCodeCheckErrorCode.value =
                    (_verifyCodeCheckState.value as APIResponse.Error).errorCode!!
                Log.d(
                    "ChangePwViewModel", "verifyCodeCheck:" +
                            " ${(_verifyCodeCheckState.value as APIResponse.Error).message} " +
                            "${(_verifyCodeCheckState.value as APIResponse.Error).errorCode}"
                )
            }
        }
    }

    fun checkIsValidPw(pw: String) {
        // 비밀번호 형식 확인
        // 6~20자, 영문, 숫자, 특수문자 포함
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{6,20}\$"
        _isValidPw.value = pw.matches(pwPattern.toRegex())
    }

    fun resetVerifyEmailSendState() {
        _verifyEmailSendState.value = APIResponse.Empty()
    }

    fun resetVerifyCodeCheckState() {
        _verifyCodeCheckState.value = APIResponse.Empty()
    }

    fun changePw(email: String, pw: String) {
        viewModelScope.launch {
            _changePwState.value = accountRepository.changePw(email, pw)

            if (_changePwState.value is APIResponse.Success) {
                tokenRepository.deleteToken()
            }

            if (_changePwState.value is APIResponse.Error) {
                Log.d(
                    "ChangePwViewModel", "changePw:" +
                            " ${(_changePwState.value as APIResponse.Error).message} " +
                            "${(_changePwState.value as APIResponse.Error).errorCode}"
                )
            }
        }
    }
}