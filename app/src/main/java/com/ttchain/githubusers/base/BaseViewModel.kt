package com.ttchain.githubusers.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val disposables = CompositeDisposable()

    fun add(job: Disposable) {
        disposables.add(job)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun forceClear() {
        disposables.clear()
    }
}