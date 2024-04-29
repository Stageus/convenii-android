package com.example.convenii.service

import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterData
import com.example.convenii.model.account.SignInData
import com.example.convenii.model.main.ProductData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Account -------------------------------------
    @POST("account/login")
    suspend fun signIn(
        @Body body: SignInData.RequestBody
    ): retrofit2.Response<SignInData.ResponseBody>

    @POST("account/verify-email/send")
    suspend fun verifyEmailSend(
        @Body body: RegisterData.VerifyEmailSendRequestBody
    ): retrofit2.Response<RegisterData.VerifyEmailSendResponseBody>

    @POST("account/verify-email/check")
    suspend fun verifyEmailCheck(
        @Body body: RegisterData.VerifyEmailCheckRequestBody
    ): retrofit2.Response<CommonResponseData.Response>

    @POST("account")
    suspend fun register(
        @Body body: RegisterData.RegisterRequestBody
    ): retrofit2.Response<RegisterData.RegisterResponseBody>

    //Main -------------------------------------
    @GET("product/company/{companyIdx}")
    suspend fun getProductCompany(
        @Path("companyIdx") companyIdx: Int,
        @Query("page") page: Int,
        @Query("option") option: String
    ): retrofit2.Response<ProductData.ProductCompanyResponseData>

}