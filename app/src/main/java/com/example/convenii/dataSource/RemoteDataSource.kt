package com.example.convenii.dataSource

import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterData
import com.example.convenii.model.account.SignInData
import com.example.convenii.model.main.ProductData
import com.example.convenii.service.ApiService
import retrofit2.Response
import javax.inject.Inject


interface RemoteDataSource {
    // account
    suspend fun signIn(email: String, pw: String): Response<SignInData.ResponseBody>
    suspend fun verifyEmailSend(email: String): Response<RegisterData.VerifyEmailSendResponseBody>
    suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): Response<CommonResponseData.Response>

    suspend fun register(
        email: String,
        pw: String,
        nickname: String
    ): Response<RegisterData.RegisterResponseBody>

    //main -------------------------------------
    suspend fun getProductCompany(
        companyIdx: Int,
        page: Int,
        option: String
    ): Response<ProductData.ProductCompanyResponseData>
}


class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {
    // account -------------------------------------
    override suspend fun signIn(email: String, pw: String): Response<SignInData.ResponseBody> {
        val requestBody = SignInData.RequestBody(email = email, pw = pw)
        return apiService.signIn(requestBody)
    }

    override suspend fun verifyEmailSend(email: String): Response<RegisterData.VerifyEmailSendResponseBody> {
        val requestBody = RegisterData.VerifyEmailSendRequestBody(email = email)
        return apiService.verifyEmailSend(requestBody)
    }

    override suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): Response<CommonResponseData.Response> {
        val requestBody = RegisterData.VerifyEmailCheckRequestBody(
            email = email,
            verificationCode = verificationCode
        )
        return apiService.verifyEmailCheck(requestBody)
    }

    override suspend fun register(
        email: String,
        pw: String,
        nickname: String
    ): Response<RegisterData.RegisterResponseBody> {
        val requestBody = RegisterData.RegisterRequestBody(
            email = email,
            pw = pw,
            nickname = nickname
        )
        return apiService.register(requestBody)
    }

    //main -------------------------------------
    override suspend fun getProductCompany(
        companyIdx: Int,
        page: Int,
        option: String
    ): Response<ProductData.ProductCompanyResponseData> {
        return apiService.getProductCompany(companyIdx, page, option)
    }
}