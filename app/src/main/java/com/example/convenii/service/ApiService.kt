package com.example.convenii.service

import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterData
import com.example.convenii.model.account.SignInData
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
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


}