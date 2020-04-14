package com.ttchain.githubusers.koin

import com.ttchain.githubusers.BuildConfig
import com.ttchain.githubusers.Constants
import com.ttchain.githubusers.getApiAddress
import com.ttchain.githubusers.net.GitHubApi
import com.ttchain.githubusers.net.HeaderAcceptInterceptor
import com.ttchain.githubusers.net.SmsApi
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T> providesApi(client: OkHttpClient, url: String): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(T::class.java)
}

val networkModule = module {

    single {
        val logging = HttpLoggingInterceptor()
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS).apply {
                // add HttpLoggingInterceptor
                if (BuildConfig.DEBUG)
                    addNetworkInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            .build()
    }

    val sms = "sms"
    single(named(sms)) {
        val logging = HttpLoggingInterceptor()
        OkHttpClient.Builder()
//            .addNetworkInterceptor(HeaderAcceptInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).apply {
                if (BuildConfig.DEBUG)
                    addNetworkInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            // change host
            .addInterceptor(ApiAddressInterceptor())
            .build()
    }

    single { providesApi<GitHubApi>(get(), Constants.API_HOST) }
    single { providesApi<SmsApi>(get(named(sms)), getApiAddress()) }
}

class ApiAddressInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val newUrl = request.url().newBuilder()
            .host(HttpUrl.parse(getApiAddress())?.host().orEmpty())
            .port(
                HttpUrl.parse(getApiAddress())?.port() ?: HttpUrl.defaultPort(
                    HttpUrl.parse(getApiAddress())?.scheme() ?: "https"
                )
            )
            .build()
        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}