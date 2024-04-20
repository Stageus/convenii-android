package com.example.convenii.service

import okhttp3.Interceptor


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer ${"token"}")
            .build()
        return chain.proceed(newRequest)
    }
}
