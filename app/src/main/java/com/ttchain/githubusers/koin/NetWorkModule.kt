package com.ttchain.githubusers.koin

import com.google.gson.GsonBuilder
import com.ttchain.githubusers.BuildConfig
import com.ttchain.githubusers.Constants
import com.ttchain.githubusers.net.GitHubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T> providesApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

val networkModule = module {
    single {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.create()
    }

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

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(Constants.API_HOST)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single { providesApi<GitHubApi>(get()) }
}