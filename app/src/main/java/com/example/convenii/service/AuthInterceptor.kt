package com.example.convenii.service

import com.example.convenii.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import javax.inject.Inject


class AuthInterceptor @Inject constructor(private val tokenRepository: TokenRepository) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()

        runBlocking {
            val tokenData = tokenRepository.fetchToken()
            if (tokenData != null) {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer ${tokenData.accessToken}")
                    .build()
            }
        }

        return chain.proceed(request)
    }
}
