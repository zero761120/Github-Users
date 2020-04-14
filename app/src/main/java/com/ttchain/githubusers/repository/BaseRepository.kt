package com.ttchain.githubusers.repository

import com.ttchain.githubusers.data.ApiResult
import com.ttchain.githubusers.net.ApiCodeEnum
import com.ttchain.githubusers.net.ApiError
import com.ttchain.githubusers.net.RepositoryResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class BaseRepository {
    private val apiSchedulers = Schedulers.newThread()

    fun <T> getApi(observable: Single<T>): Single<T> {
        return observable.subscribeOn(apiSchedulers)
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun <T> checkError(result: ApiResult<T>): Throwable {
        return if (ApiCodeEnum.values().contains(result.code)) {
            ApiError(result.code, result.message)
        } else {
            Throwable(result.message)
        }
    }

    fun <T> checkResultRepository(result: ApiResult<T>): RepositoryResult<T> {
        return when (result.code) {
            ApiCodeEnum.NUMBER_1 -> {
                RepositoryResult.success(result.data)
            }
            else -> {
                throw checkError(result)
            }
        }
    }
}