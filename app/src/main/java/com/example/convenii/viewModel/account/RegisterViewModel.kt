package com.example.convenii.viewModel.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.account.RegisterData
import com.example.convenii.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _isItEmail = MutableStateFlow(false)
    val isItEmail: StateFlow<Boolean> = _isItEmail

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isEmailSend =
        MutableStateFlow<APIResponse<RegisterData.VerifyEmailSendResponseBody>>(APIResponse.Empty())
    val isEmailSend: StateFlow<APIResponse<RegisterData.VerifyEmailSendResponseBody>> = _isEmailSend


    fun checkIsItEmail(email: String) {
        // 이메일 형식인지 확인
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (email.matches(emailPattern.toRegex())) {
            _isItEmail.value = true
        } else {
            _isItEmail.value = false
        }
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun verifyEmailSend(email: String) {
        viewModelScope.launch {
            _isEmailSend.value = accountRepository.verifyEmailSend(email)
            if (isEmailSend.value is APIResponse.Success) _email.value = email
        }
    }

    fun resetIsEmailSend() {
        _isEmailSend.value = APIResponse.Empty()
    }

    fun verifyEmailCheck(verificationCode: String) {
        viewModelScope.launch {
            try {
                val result = accountRepository.verifyEmailCheck(_email.value, verificationCode)
                Log.d("RegisterViewModel", "verifyEmailCheck: $result")
                // 성공 처리, 결과에 따른 UI 업데이트
            } catch (e: Exception) {
                Log.d("RegisterViewModel", "verifyEmailCheck: ${e.message}")
            }
        }
    }

}