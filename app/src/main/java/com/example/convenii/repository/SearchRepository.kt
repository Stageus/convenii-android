package com.example.convenii.repository

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductModel


interface SearchRepository {
    suspend fun getSearchData(
        keyword: String?,
        eventFilter: String?,
        categoryFilter: String?,
        page: Int
    ): APIResponse<ProductModel.ProductCompanyResponseData>

}

class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {
    override suspend fun getSearchData(
        keyword: String?,
        eventFilter: String?,
        categoryFilter: String?,
        page: Int
    ): APIResponse<ProductModel.ProductCompanyResponseData> {
        try {
            val response = remoteDataSource.getSearchData(
                keyword = keyword,
                eventFilter = eventFilter,
                categoryFilter = categoryFilter,
                page = page
            )
            return if (response.isSuccessful) {
                APIResponse.Success(response.body()!!)
            } else {
                APIResponse.Error(
                    message = "message: ${
                        response.errorBody()!!.string()
                    }",
                    errorCode = response.code().toString()
                )
            }
        } catch (e: Exception) {
            return APIResponse.Error(
                message = "Get product detail failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

}