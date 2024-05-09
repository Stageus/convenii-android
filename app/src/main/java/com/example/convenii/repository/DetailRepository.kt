package com.example.convenii.repository

import android.util.Log
import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.detail.ProductDetailModel
import com.example.convenii.model.detail.ReviewModel


interface DetailRepository {
    suspend fun getDetailProduct(
        productIdx: Int
    ): APIResponse<ProductDetailModel.ProductDetailResponseData>

    suspend fun getProductReview(
        productIdx: Int,
        page: Int
    ): APIResponse<ReviewModel.GetReviewResponseData>

    suspend fun postProductReview(
        productIdx: Int,
        body: ReviewModel.PostReviewRequestData
    ): APIResponse<CommonResponseData.Response>

    suspend fun deleteProduct(
        productIdx: Int
    ): APIResponse<CommonResponseData.Response>
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

    override suspend fun getProductReview(
        productIdx: Int,
        page: Int
    ): APIResponse<ReviewModel.GetReviewResponseData> {
        try {
            val response = remoteDataSource.getProductReview(productIdx, page)
            Log.d("DetailRepositoryImpl", "getProductReview: ${response.body()}")
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
                message = "Get product review failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun postProductReview(
        productIdx: Int,
        body: ReviewModel.PostReviewRequestData
    ): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.postProductReview(productIdx, body)
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
                message = "Post product review failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun deleteProduct(productIdx: Int): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.deleteProduct(productIdx)
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
                message = "Delete product failed: ${e.message}",
                errorCode = "500"
            )
        }
    }
}