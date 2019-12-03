package com.example.izbela.utils

import com.example.izbela.shared.ITokenCache
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val TOKEN_HEADER = "AUTHORIZATION"

class AuthorizationInterceptor @Inject constructor(private val tokenCache: ITokenCache) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()

        val auth = tokenCache.accessToken
        val url = original.url.newBuilder()

        if (auth != null) {
            builder.header(TOKEN_HEADER, auth)
        }

        val request = builder
            .url(url.build())
            .build()

        return chain.proceed(request)
    }

}