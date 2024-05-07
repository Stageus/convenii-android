package com.example.convenii.repository

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.profile.ProfileModel


interface ProfileRepository {
    suspend fun getProfile(): APIResponse<ProfileModel.ProfileResponseData>
    suspend fun deleteAccount(): APIResponse<CommonResponseData.Response>
}

class ProfileRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ProfileRepository {

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

    override suspend fun deleteAccount(): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.deleteAccount()
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
                message = "Delete account failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

}