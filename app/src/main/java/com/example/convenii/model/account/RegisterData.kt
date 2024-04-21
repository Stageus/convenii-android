package com.example.convenii.model.account

class RegisterData {

    data class VerifyEmailSendRequestBody(
        val email: String
    )

    data class VerifyEmailSendResponseBody(
        val message: String? = null,
    )

    data class VerifyEmailCheckRequestBody(
        val email: String,
        val verificationCode: String
    )

    data class RegisterRequestBody(
        val email: String,
        val pw: String,
        val nickname: String
    )

    data class RegisterResponseBody(
        val accessToken: String
    )
}