package com.example.moviesapplication.data.network.interceptor

import com.example.moviesapplication.utils.AppConstants.API_KEY
import com.example.moviesapplication.utils.AppConstants.API_PARAM
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.reflect.Constructor
import javax.inject.Inject

/*Api call Interceptor class , Here we add the API key and value for request Header */

class AuthenticationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url = originalHttpUrl.newBuilder().addQueryParameter(API_PARAM, API_KEY).build()
        val requestBuilder: Request.Builder = original.newBuilder().url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request = request)
    }
}