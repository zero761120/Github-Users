package com.ttchain.githubusers.ui.sms

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ttchain.githubusers.App
import com.ttchain.githubusers.R
import com.ttchain.githubusers.base.BaseViewModel
import com.ttchain.githubusers.data.LoginResponse
import com.ttchain.githubusers.getSHA512
import com.ttchain.githubusers.repository.SmsRepository
import com.ttchain.githubusers.tools.TimeUtils

class SmsViewModel(private val context: Context, private val smsRepository: SmsRepository) :
    BaseViewModel() {
    var loginResult = MutableLiveData<LoginResponse>()
    var loginError = MutableLiveData<String>()
    var loginId = ""
    var secretKey = ""

    fun login(account: String, password: String) {
        loginId = account
        add(
            smsRepository.login(account, password)
                .subscribe({
                    secretKey = it.data?.secretKey.orEmpty()
                    App.apiAddress = it.data?.callbackDomain.orEmpty()
                    loginResult.value = it.data
                }, {
                    loginError.value = it.message
                })
        )
    }

    var receiptResult = MutableLiveData<String>()
    var receiptError = MutableLiveData<String>()
    var receiptText = ""
    var bankAccountNumber = ""

    fun receipt(
        message: String
    ) {
        val hash = "$loginId$bankAccountNumber$message$secretKey".getSHA512()
        add(
            smsRepository.receipt(loginId, bankAccountNumber, message, hash)
                .subscribe({
                    receiptResult.value =
                        "${getNowTimeString()}: ${context.getString(R.string.sms_success)}"
                }, {
                    receiptError.value = "${getNowTimeString()}: ${it.message}"
                })
        )
    }

    fun getNowTimeString(): String {
        return TimeUtils.getFormatTimeInDefaultLocale(TimeUtils.getNowTimestamp())
    }
}