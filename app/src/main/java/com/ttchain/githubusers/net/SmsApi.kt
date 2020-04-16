package com.ttchain.githubusers.net

import com.ttchain.githubusers.data.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface SmsApi {

    /**
     * 承兌商登入並取得回傳匯款回條資訊
     */
    @Headers("Content-Type:application/json")
    @POST("acceptor/receipt/login")
    fun acceptorLogin(
        @Body unit: LoginRequest
    ): Single<ApiResult<LoginResponse>>

    /**
     * 確認承兌商收款銀行是否存在
     */
    @Headers("Content-Type:application/json")
    @POST("acceptor/bank")
    fun acceptorBank(
        @Body unit: BankRequest
    ): Single<ApiResult<Boolean>>

    /**
     * 建立承兌商收款回條資訊
     */
    @Headers("Content-Type:application/json")
    @POST("acceptor/receipt")
    fun acceptorReceipt(
        @Body unit: ReceiptRequest
    ): Single<ApiResult<Boolean>>
}