package com.ttchain.githubusers.net


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderAcceptInterceptor : Interceptor {

    val TAG = javaClass.simpleName

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        builder.addHeader("Content-Type", "application/json")
//        builder.addHeader("Accept", "application/json")
//        builder.addHeader("Accept-Language", "zh-cn")

        val request = builder.build()

        return chain.proceed(request)
    }
}
