package com.example.convenii.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.repository.TokenRepository
import com.example.convenii.view.ConveniiScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val tokenRepository: TokenRepository) :
    ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: MutableStateFlow<Boolean> get() = _isReady

    private val _isToken = MutableStateFlow(false)
    val isToken: MutableStateFlow<Boolean> get() = _isToken

    private val _screen: MutableStateFlow<String> = MutableStateFlow("")
    val screen: MutableStateFlow<String> get() = _screen


    init {
        viewModelScope.launch {
            _isToken.value = tokenRepository.checkToken()
            if (_isToken.value) {
                _screen.value = ConveniiScreen.Home.route
            } else {
                _screen.value = "start"
            }
            _isReady.value = true
        }
    }
}