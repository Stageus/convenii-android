package com.example.convenii.viewModel.main.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductModel
import com.example.convenii.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    private val _bookmarkDataState =
        MutableStateFlow<APIResponse<ProductModel.ProductCompanyResponseData>>(APIResponse.Empty())
    val bookmarkDataState: StateFlow<APIResponse<ProductModel.ProductCompanyResponseData>> =
        _bookmarkDataState

    private val _bookmarkData = MutableStateFlow(mutableListOf<ProductModel.ProductCompanyData>())
    val bookmarkData: StateFlow<MutableList<ProductModel.ProductCompanyData>> = _bookmarkData

    private val _isDataEnded = MutableStateFlow(false)
    val isDataEnded: StateFlow<Boolean> = _isDataEnded

    private var _page = 1

    fun getBookmarkData() {
        viewModelScope.launch {
            _bookmarkDataState.value = bookmarkRepository.getAllBookmark(
                page = _page
            )
            if (_bookmarkDataState.value is APIResponse.Success) {
                Log.d(
                    "BookmarkViewModel",
                    "getBookmarkData: ${(_bookmarkDataState.value as APIResponse.Success).data}"
                )
                val newData =
                    (_bookmarkDataState.value as APIResponse.Success).data!!.data.productList
                if (newData.isEmpty()) {
                    _isDataEnded.value = true
                    return@launch
                }
                _bookmarkData.value = ArrayList(_bookmarkData.value).apply {
                    addAll(newData)
                }
                _page += 1
            } else {
                Log.d(
                    "BookmarkViewModel",
                    "getBookmarkData: ${(_bookmarkDataState.value as APIResponse.Error).message} ${(_bookmarkDataState.value as APIResponse.Error).errorCode}"
                )
                _isDataEnded.value = true
            }
        }
    }
}