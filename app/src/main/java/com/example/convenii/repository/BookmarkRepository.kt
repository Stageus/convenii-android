package com.example.convenii.repository

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.main.ProductModel


interface BookmarkRepository {
    suspend fun getAllBookmark(): APIResponse<ProductModel.ProductCompanyResponseData>
    suspend fun postBookmark(productIdx: Int): APIResponse<CommonResponseData.Response>
    suspend fun deleteBookmark(productIdx: Int): APIResponse<CommonResponseData.Response>
}

class BookmarkRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : BookmarkRepository {
    override suspend fun getAllBookmark(): APIResponse<ProductModel.ProductCompanyResponseData> {
        try {
            val response = remoteDataSource.getAllBookmark()
            return if (response.isSuccessful) {
                APIResponse.Success(data = response.body())
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
                message = "Get all bookmark failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun postBookmark(productIdx: Int): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.postBookmark(productIdx)
            return if (response.isSuccessful) {
                APIResponse.Success(data = response.body())
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
                message = "Post bookmark failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun deleteBookmark(productIdx: Int): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.deleteBookmark(productIdx)
            return if (response.isSuccessful) {
                APIResponse.Success(data = response.body())
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
                message = "Delete bookmark failed: ${e.message}",
                errorCode = "500"
            )
        }
    }
}