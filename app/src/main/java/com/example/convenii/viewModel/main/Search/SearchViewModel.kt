package com.example.convenii.viewModel.main.Search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductModel
import com.example.convenii.repository.SearchHistoryRepository
import com.example.convenii.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val searchHistoryRepository: SearchHistoryRepository

) : ViewModel() {
    private val _selectedEventFilter = MutableStateFlow(EventFilterState())
    val selectedEventFilter = _selectedEventFilter.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow(CategoryFilterState())
    val selectedCategoryFilter = _selectedCategoryFilter.asStateFlow()

    private val _page = MutableStateFlow(1)

    private val _isDataEnded = MutableStateFlow(false)
    val isDataEnded = _isDataEnded.asStateFlow()

    private val _keyword = MutableStateFlow("")
    val keyword = _keyword.asStateFlow()

    private val _searchDataState =
        MutableStateFlow<APIResponse<ProductModel.ProductCompanyResponseData>>(APIResponse.Empty())
    val searchDataState = _searchDataState.asStateFlow()
    private val _searchData = MutableStateFlow(mutableListOf<ProductModel.ProductCompanyData>())
    val searchData = _searchData.asStateFlow()
    private val _authState = MutableStateFlow("")
    val authState = _authState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    fun getSearchData(
    ) {
        val eventFilter: MutableList<Int> = selectedEventFilter.value.run {
            mutableListOf<Int>().apply {
                if (onePlus) add(1)
                if (twoPlus) add(2)
                if (sale) add(3)
                if (bonus) add(4)
                if (other) add(5)
            }
        }
        val categoryFilter: MutableList<Int> = selectedCategoryFilter.value.run {
            mutableListOf<Int>().apply {
                if (drink) add(1)
                if (snack) add(2)
                if (food) add(3)
                if (iceCream) add(4)
                if (daily) add(5)
                if (other) add(6)
            }
        }

        val tmp = eventFilter.toString()

        Log.d(
            "SearchViewModel",
            "getSearchData: $tmp $categoryFilter ${keyword.value} ${_page.value}"
        )
        viewModelScope.launch {
            _searchDataState.value = searchRepository.getSearchData(
                keyword = keyword.value,
                page = _page.value,
                eventFilter = eventFilter.toString(),
                categoryFilter = categoryFilter.toString()
            )
            if (_searchDataState.value is APIResponse.Success) {
                Log.d(
                    "SearchViewModel",
                    "getSearchData: ${(_searchDataState.value as APIResponse.Success).data!!.data.productList}"
                )
                _authState.value = (_searchDataState.value as APIResponse.Success).data!!.authStatus
                val newData =
                    (_searchDataState.value as APIResponse.Success).data!!.data.productList
                _authState.value = (_searchDataState.value as APIResponse.Success).data!!.authStatus
                if (newData.isEmpty()) {
                    _isDataEnded.value = true
                    return@launch
                }
                _searchData.value = ArrayList(_searchData.value).apply {
                    addAll(newData)
                }
            } else {
                Log.d(
                    "SearchViewModel",
                    "getSearchData: ${(_searchDataState.value as APIResponse.Error).message}"
                )
            }
        }


    }

    fun resetData() {
        _searchData.value.clear()
        _page.value = 1
    }

    fun resetFilter() {
        _selectedEventFilter.value = EventFilterState()
        _selectedCategoryFilter.value = CategoryFilterState()
    }

    fun toggleEventFilter(filter: String) {
        _selectedEventFilter.value = when (filter) {
            "onePlus" -> _selectedEventFilter.value.copy(onePlus = !_selectedEventFilter.value.onePlus)
            "twoPlus" -> _selectedEventFilter.value.copy(twoPlus = !_selectedEventFilter.value.twoPlus)
            "sale" -> _selectedEventFilter.value.copy(sale = !_selectedEventFilter.value.sale)
            "bonus" -> _selectedEventFilter.value.copy(bonus = !_selectedEventFilter.value.bonus)
            "other" -> _selectedEventFilter.value.copy(other = !_selectedEventFilter.value.other)
            else -> _selectedEventFilter.value
        }
        resetData()
        getSearchData()
    }

    fun toggleCategoryFilter(filter: String) {
        _selectedCategoryFilter.value = when (filter) {
            "drink" -> _selectedCategoryFilter.value.copy(drink = !_selectedCategoryFilter.value.drink)
            "snack" -> _selectedCategoryFilter.value.copy(snack = !_selectedCategoryFilter.value.snack)
            "food" -> _selectedCategoryFilter.value.copy(food = !_selectedCategoryFilter.value.food)
            "iceCream" -> _selectedCategoryFilter.value.copy(iceCream = !_selectedCategoryFilter.value.iceCream)
            "daily" -> _selectedCategoryFilter.value.copy(daily = !_selectedCategoryFilter.value.daily)
            "other" -> _selectedCategoryFilter.value.copy(other = !_selectedCategoryFilter.value.other)
            else -> _selectedCategoryFilter.value
        }
        resetData()
        getSearchData()
    }

    fun setKeyword(keyword: String) {
        _keyword.value = keyword
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            _searchHistory.value = searchHistoryRepository.fetchSearchHistory()
        }
    }

    fun saveSearchHistory(query: String) {
        viewModelScope.launch {
            searchHistoryRepository.saveSearchHistory(query)
        }
    }

    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {
            searchHistoryRepository.deleteSearchQuery(query)
            getSearchHistory()
        }
    }
}

data class EventFilterState(
    val onePlus: Boolean = false,
    val twoPlus: Boolean = false,
    val sale: Boolean = false,
    val bonus: Boolean = false,
    val other: Boolean = false
)

data class CategoryFilterState(
    val drink: Boolean = false,
    val snack: Boolean = false,
    val food: Boolean = false,
    val iceCream: Boolean = false,
    val daily: Boolean = false,
    val other: Boolean = false
)