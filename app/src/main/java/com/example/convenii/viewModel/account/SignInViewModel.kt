package com.example.convenii.viewModel.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.account.SignInData
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

    private val _signInState: MutableStateFlow<APIResponse<SignInData.ResponseBody>> =
        MutableStateFlow(APIResponse.Empty())
    val signInState: StateFlow<APIResponse<SignInData.ResponseBody>> = _signInState

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
        _signInState.value = APIResponse.Loading()
        viewModelScope.launch {
            val result = accountRepository.signIn(tmpEmail, tmpPw)

            if (result.data != null) {
                _signInState.value = APIResponse.Success(data = result.data)
                Log.d("SignInViewModel", _signInState.value.data!!.accessToken)
            } else {
                _signInState.value = APIResponse.Error(
                    message = result.message!!,
                    errorCode = result.errorCode!!
                )
                Log.d(
                    "SignInViewModel", "${_signInState.value.message.toString()} errorCode: ${
                        _signInState.value
                            .errorCode
                    }"
                )

            }
            // 성공 처리, 결과에 따른 UI 업데이트

        }
    }

}