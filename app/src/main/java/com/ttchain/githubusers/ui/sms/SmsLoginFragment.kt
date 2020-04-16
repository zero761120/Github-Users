package com.ttchain.githubusers.ui.sms

import android.os.Bundle
import androidx.lifecycle.observe
import com.ttchain.githubusers.*
import com.ttchain.githubusers.base.BaseFragment
import kotlinx.android.synthetic.main.sms_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SmsLoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = SmsLoginFragment()
        const val loginPath = "acceptor/receipt/login"
    }

    override val layoutId: Int
        get() = R.layout.sms_login

    private val viewModel by sharedViewModel<SmsViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    override fun initView() {
        editTextApiAddress.setText(App.preferenceHelper.userHost)
        editTextAccount.setText(App.preferenceHelper.userAccount)
        editTextPassword.setText(App.preferenceHelper.userPassword)
        loginButton.setOnClickListener {
            requireActivity().hideKeyboard()
            val apiAddress = editTextApiAddress.text.toString()
            App.apiAddress = when {
                apiAddress.contains(loginPath, true) ->
                    apiAddress.replace(loginPath, "")
                else -> apiAddress
            }
            val loginId = editTextAccount.text.toString()
            val password = editTextPassword.text.toString()
            if (apiAddress.isBlank() || loginId.isBlank() || password.isBlank()) {
                childFragmentManager.showSendToast(
                    false,
                    getString(R.string.error),
                    getString(R.string.empty_error)
                )
            } else {
                onShowLoading()
                App.preferenceHelper.userHost = apiAddress
                App.preferenceHelper.userAccount = loginId
                App.preferenceHelper.userPassword = password
                viewModel.login(loginId, password)
            }
        }
    }

    private fun initData() {
        viewModel.apply {
            loginError.observe(viewLifecycleOwner) {
                onHideLoading()
                childFragmentManager.showSendToast(false, getString(R.string.error), it)
            }
        }
    }
}