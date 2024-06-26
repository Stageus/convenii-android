package com.example.convenii.repository

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.main.ProductModel
import com.example.convenii.model.profile.ProfileModel


interface MainRepository {
    suspend fun getProductCompany(
        companyIdx: Int,
        page: Int,
        option: String
    ): APIResponse<ProductModel.ProductCompanyResponseData>

    suspend fun getProfile(): APIResponse<ProfileModel.ProfileResponseData>
}

class MainRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MainRepository {
    override suspend fun getProductCompany(
        companyIdx: Int,
        page: Int,
        option: String
    ): APIResponse<ProductModel.ProductCompanyResponseData> {
        try {
            val response = remoteDataSource.getProductCompany(companyIdx, page, option)
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
                message = "Get product company failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun getProfile(): APIResponse<ProfileModel.ProfileResponseData> {
        try {
            val response = remoteDataSource.getProfileData()
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
                message = "Get profile failed: ${e.message}",
                errorCode = "500"
            )
        }
    }
}