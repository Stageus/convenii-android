package com.example.convenii.view.account

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
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

}