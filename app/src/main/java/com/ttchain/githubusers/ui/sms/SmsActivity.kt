package com.ttchain.githubusers.ui.sms

import android.os.Bundle
import androidx.lifecycle.observe
import com.ttchain.githubusers.R
import com.ttchain.githubusers.addFragment
import com.ttchain.githubusers.base.BaseActivity
import com.ttchain.githubusers.changeFragment
import com.ttchain.githubusers.tools.Gzip
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SmsActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.sms_activity

    private val viewModel by viewModel<SmsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        Timber.d("NowTime: ${viewModel.getNowTimeString()}")
        val text = "aaaa1234"
        val enText = Gzip.compress(text)
        Timber.d("enText: $enText")
        val deText = Gzip.decompress(enText)
        Timber.d("deText: $deText")
        changeFragment(R.id.container, SmsLoginFragment.newInstance())
    }

    private fun initData() {
        viewModel.loginResult.observe(this) {
            onHideLoading()
            addFragment(R.id.container, SmsReceiptFragment.newInstance())
        }
    }
}