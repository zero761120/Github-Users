package com.ttchain.githubusers.net

class ApiError(val errorCode: ApiCodeEnum, val errorMessage: String) : Throwable(errorMessage) {
}