package com.ttchain.githubusers.ui.sms

import android.os.Bundle
import androidx.lifecycle.observe
import com.ttchain.githubusers.R
import com.ttchain.githubusers.addFragment
import com.ttchain.githubusers.base.BaseActivity
import com.ttchain.githubusers.changeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        changeFragment(R.id.container, SmsLoginFragment.newInstance())
    }

    private fun initData() {
        viewModel.loginResult.observe(this) {
            addFragment(R.id.container, SmsReceiptFragment.newInstance())
        }
    }
}