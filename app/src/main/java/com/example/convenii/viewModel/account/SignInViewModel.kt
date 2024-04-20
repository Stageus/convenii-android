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
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isEnabled = MutableStateFlow(false)
    val isEnabled: StateFlow<Boolean> = _isEnabled

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setIsEnabled() {
        _isEnabled.value = !_isEnabled.value
    }

    fun setIsError() {
        _isError.value = !_isError.value
    }

    fun signIn(email: String, pw: String) {
        val tmpEmail = "juneh2633@gmail.com"
        val tmpPw = "asdf1234@"
        viewModelScope.launch {
            try {
                val result = accountRepository.signIn(tmpEmail, tmpPw)
                Log.d("SignInViewModel", "signIn: $result")
                // 성공 처리, 결과에 따른 UI 업데이트
            } catch (e: Exception) {
                Log.d("SignInViewModel", "signIn: ${e.message}")

            }
        }
    }

}