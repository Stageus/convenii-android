package com.example.convenii.repository

import android.util.Log
import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterData
import com.example.convenii.model.account.SignInData


interface AccountRepository {
    suspend fun signIn(email: String, pw: String): APIResponse<SignInData.ResponseBody>
    suspend fun verifyEmailSend(email: String): RegisterData.VerifyEmailSendResponseBody
    suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): CommonResponseData.Response
}

class AccountRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : AccountRepository {
    override suspend fun signIn(email: String, pw: String): APIResponse<SignInData.ResponseBody> {
        val response = remoteDataSource.signIn(email, pw)
        return if (response.isSuccessful) {
            APIResponse.Success(data = response.body())
        } else {
            APIResponse.Error(
                message = "Error code: ${response.code()} ${
                    response.errorBody()!!.string()
                }"
            )
        }
    }

    override suspend fun verifyEmailSend(email: String): RegisterData.VerifyEmailSendResponseBody {
        try {
            val response = remoteDataSource.verifyEmailSend(email)
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                throw Exception("Error code: ${response.code()} ${response.errorBody()!!.string()}")
            }
        } catch (e: Exception) {
            // 로그 기록, 오류 메시지를 UI로 전달, 또는 특정 오류 처리
            throw Exception("Verify email send failed: ${e.message}")
        }
    }

    override suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): CommonResponseData.Response {
        try {
            val response = remoteDataSource.verifyEmailCheck(email, verificationCode)
            if (response.isSuccessful) {
                Log.d("code", response.code().toString())
                return response.body() ?: throw Exception("Response body is null")
            } else {
                throw Exception("Error code: ${response.code()} ${response.errorBody()!!.string()}")
            }
        } catch (e: Exception) {
            // 로그 기록, 오류 메시지를 UI로 전달, 또는 특정 오류 처리
            throw Exception("Verify email check failed: ${e.message}")
        }
    }


}

sealed class APIResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : APIResponse<T>(data)
    class Loading<T>(data: T? = null) : APIResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : APIResponse<T>(data, message)
}
