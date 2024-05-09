package com.example.convenii.repository

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.product.ProductAddModel
import okhttp3.MultipartBody


interface ProductRepository {
    suspend fun addProduct(
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): APIResponse<CommonResponseData.Response>

    suspend fun editProduct(
        productIdx: Int,
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part?,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): APIResponse<CommonResponseData.Response>
}

class ProductRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ProductRepository {
    override suspend fun addProduct(
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.addProduct(
                categoryIdx = categoryIdx,
                name = name,
                price = price,
                image = image,
                eventInfo = eventInfo
            )
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
                message = "Add product failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun editProduct(
        productIdx: Int,
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part?,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.editProduct(
                productIdx = productIdx,
                categoryIdx = categoryIdx,
                name = name,
                price = price,
                image = image,
                eventInfo = eventInfo
            )
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
                message = "Edit product failed: ${e.message}",
                errorCode = "500"
            )
        }
    }
}