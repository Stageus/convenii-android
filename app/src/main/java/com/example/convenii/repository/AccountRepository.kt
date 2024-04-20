package com.example.convenii.repository

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.APIResponse
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterData
import com.example.convenii.model.account.SignInData


interface AccountRepository {
    suspend fun signIn(email: String, pw: String): APIResponse<SignInData.ResponseBody>
    suspend fun verifyEmailSend(email: String): APIResponse<RegisterData.VerifyEmailSendResponseBody>
    suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): APIResponse<CommonResponseData.Response>
}

class AccountRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : AccountRepository {
    override suspend fun signIn(email: String, pw: String): APIResponse<SignInData.ResponseBody> {
        try {
            val response = remoteDataSource.signIn(email, pw)
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
                message = "Sign in failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun verifyEmailSend(email: String): APIResponse<RegisterData.VerifyEmailSendResponseBody> {
        try {
            val response = remoteDataSource.verifyEmailSend(email)
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
            // 로그 기록, 오류 메시지를 UI로 전달, 또는 특정 오류 처리
            return APIResponse.Error(
                message = "Verify email send failed: ${e.message}",
                errorCode = "500"
            )
        }
    }

    override suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): APIResponse<CommonResponseData.Response> {
        try {
            val response = remoteDataSource.verifyEmailCheck(email, verificationCode)
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
            // 로그 기록, 오류 메시지를 UI로 전달, 또는 특정 오류 처리
            return APIResponse.Error(
                message = "Verify email check failed: ${e.message}",
                errorCode = "500"
            )
        }
    }


}

