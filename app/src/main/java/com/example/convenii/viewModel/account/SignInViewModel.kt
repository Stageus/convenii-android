package com.example.convenii.viewModel.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.account.SignInData
import com.example.convenii.repository.AccountRepository
import com.example.convenii.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val tokenRepository: TokenRepository
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
        viewModelScope.launch {
            _signInState.value = accountRepository.signIn(email, pw)
            if (_signInState.value is APIResponse.Success) {
                val token = SignInData.TokenData(
                    (_signInState.value as APIResponse.Success).data!!.accessToken
                )
                tokenRepository.saveToken(token)
            } else {
                Log.d(
                    "SignInViewModel",
                    (_signInState.value as APIResponse.Error).message.toString()
                )
                Log.d(
                    "SignInViewModel",
                    (_signInState.value as APIResponse.Error).errorCode.toString()
                )
            }
        }
    }

}