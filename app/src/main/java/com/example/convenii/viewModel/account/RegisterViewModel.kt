package com.example.convenii.viewModel.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _isEmailSend = MutableStateFlow(false)
    val isEmailSend: StateFlow<Boolean> = _isEmailSend


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
            try {
                val result = accountRepository.verifyEmailSend(email)
                Log.d("RegisterViewModel", "verifyEmailSend: $result")
                _isEmailSend.value = true
                _email.value = email
            } catch (e: Exception) {
                Log.d("RegisterViewModel", "verifyEmailSend: ${e.message}")
                _isEmailSend.value = false
            }
        }
    }

    fun resetIsEmailSend() {
        _isEmailSend.value = false
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