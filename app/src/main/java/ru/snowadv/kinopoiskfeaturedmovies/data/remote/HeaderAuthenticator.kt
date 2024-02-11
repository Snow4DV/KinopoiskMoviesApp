package ru.snowadv.kinopoiskfeaturedmovies.data.remote

import okhttp3.Interceptor
import okhttp3.Response


class HeaderAuthenticator(var token: String? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .also { newRequest -> token?.let { newRequest.header("x-api-key", it) } }
            .build()
        return chain.proceed(authenticatedRequest)
    }
}