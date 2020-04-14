package com.ttchain.githubusers.net

import com.ttchain.githubusers.App
import com.ttchain.githubusers.R
import java.util.*


/**
 * Repository處理結果
 */
class RepositoryResult<T>(val status: Status, val data: T?, val exception: Throwable?) {

    enum class Status {
        SUCCESS, ERROR
    }

    companion object {

        fun <T> success(data: T?): RepositoryResult<T> {
            return RepositoryResult(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(exception: Throwable): RepositoryResult<T> {
            return RepositoryResult(
                Status.ERROR,
                null,
                if (exception.message?.toLowerCase(Locale.getDefault())
                        ?.contains("failed to connect")!! ||
                    exception.message?.toLowerCase(Locale.getDefault())
                        ?.contains("timeout")!!
                ) {
                    Throwable(App.instance.getString(R.string.network_error))
                } else {
                    exception
                }
            )
        }
    }
}