package com.example.convenii.repository

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.detail.ProductDetailModel


interface DetailRepository {
    suspend fun getDetailProduct(
        productIdx: Int
    ): APIResponse<ProductDetailModel.ProductDetailResponseData>
}

class DetailRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : DetailRepository {
    override suspend fun getDetailProduct(
        productIdx: Int
    ): APIResponse<ProductDetailModel.ProductDetailResponseData> {
        try {
            val response = remoteDataSource.getProductDetail(productIdx)
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
                message = "Get product detail failed: ${e.message}",
                errorCode = "500"
            )
        }
    }
}