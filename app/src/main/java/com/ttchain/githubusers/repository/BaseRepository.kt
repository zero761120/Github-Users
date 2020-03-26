package com.ttchain.githubusers.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class BaseRepository {
    private val apiSchedulers = Schedulers.newThread()

    fun <T> getApi(observable: Single<T>): Single<T> {
        return observable.subscribeOn(apiSchedulers)
            .observeOn(AndroidSchedulers.mainThread())
    }
}