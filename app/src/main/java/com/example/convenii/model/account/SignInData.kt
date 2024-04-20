package com.example.convenii.model.account


class SignInData {
    data class RequestBody(
        val email: String,
        val pw: String
    )

    data class ResponseBody(
        val accessToken: String
    )

    data class TokenData(
        val accessToken: String
    )
}