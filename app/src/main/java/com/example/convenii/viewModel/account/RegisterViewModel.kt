package com.example.convenii.viewModel.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterModel
import com.example.convenii.model.account.SignInModel
import com.example.convenii.repository.AccountRepository
import com.example.convenii.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _isItEmail = MutableStateFlow(false)
    val isItEmail: StateFlow<Boolean> = _isItEmail

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isEmailSend =
        MutableStateFlow<APIResponse<RegisterModel.VerifyEmailSendResponseBody>>(APIResponse.Empty())
    val isEmailSend: StateFlow<APIResponse<RegisterModel.VerifyEmailSendResponseBody>> =
        _isEmailSend

    private val _isEmailCheck =
        MutableStateFlow<APIResponse<CommonResponseData.Response>>(APIResponse.Empty())
    val isEmailCheck: StateFlow<APIResponse<CommonResponseData.Response>> = _isEmailCheck

    private val _isValidPw = MutableStateFlow(false)
    val isValidPw: StateFlow<Boolean> = _isValidPw

    private val _pw = MutableStateFlow("")
    val pw: StateFlow<String> = _pw

    private val _registerState =
        MutableStateFlow<APIResponse<RegisterModel.RegisterResponseBody>>(APIResponse.Empty())
    val registerState: StateFlow<APIResponse<RegisterModel.RegisterResponseBody>> = _registerState


    fun checkIsItEmail(email: String) {
        // 이메일 형식인지 확인
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (email.matches(emailPattern.toRegex())) {
            _isItEmail.value = true
        } else {
            _isItEmail.value = false
        }
    }

    fun verifyEmailSend(email: String) { // 이메일 인증번호 전송
        viewModelScope.launch {
            _isEmailSend.value = accountRepository.verifyEmailSend(email)
            if (isEmailSend.value is APIResponse.Success) {
                _email.value = email
                Log.d("RegisterViewModel", isEmailSend.value.data.toString())
            } else Log.d(
                "RegisterViewModel",
                "${isEmailSend.value.message} ${isEmailSend.value.errorCode} email: $email"
            )
        }
    }

    fun resetIsEmailSend() {
        _isEmailSend.value = APIResponse.Empty()
    }

    fun verifyEmailCheck(verificationCode: String) { // 이메일 인증번호 확인
        viewModelScope.launch {
            _isEmailCheck.value = accountRepository.verifyEmailCheck(email.value, verificationCode)

            if (_isEmailCheck.value is APIResponse.Error) {
                Log.d("RegisterViewModel", _isEmailCheck.value.message.toString())
            }

        }
    }

    fun resetIsEmailCheck() {
        _isEmailCheck.value = APIResponse.Empty()
    }

    fun checkIsValidPw(pw: String) {
        // 비밀번호 형식 확인
        // 6~20자, 영문, 숫자, 특수문자 포함
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{6,20}\$"
        _isValidPw.value = pw.matches(pwPattern.toRegex())
    }

    fun setPw(pw: String) {
        _pw.value = pw
    }

    fun register(nickname: String) {
        viewModelScope.launch {
            _registerState.value = accountRepository.register(email.value, pw.value, nickname)
            if (registerState.value is APIResponse.Success) {
                val token = SignInModel.TokenData(
                    (registerState.value as APIResponse.Success).data!!.accessToken
                )
                tokenRepository.saveToken(token)
            } else {
                Log.d("RegisterViewModel", registerState.value.message.toString())
                Log.d("RegisterViewModel", registerState.value.errorCode.toString())
            }
        }
    }
}