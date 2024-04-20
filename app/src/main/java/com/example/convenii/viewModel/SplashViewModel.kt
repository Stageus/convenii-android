package com.example.convenii.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: MutableStateFlow<Boolean> get() = _isReady

    private val _isToken = MutableStateFlow(false)
    val isToken: MutableStateFlow<Boolean> get() = _isToken

    private val _screen: MutableStateFlow<String> = MutableStateFlow("")
    val screen: MutableStateFlow<String> get() = _screen


    init {
        viewModelScope.launch {
            if (_isToken.value) {
                _screen.value = "home"
            } else {
                _screen.value = "start"
            }
            _isReady.value = true

        }
    }
}