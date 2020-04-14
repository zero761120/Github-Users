package com.ttchain.githubusers.ui.sms

import android.Manifest
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.observe
import com.tbruyelle.rxpermissions2.RxPermissions
import com.ttchain.githubusers.*
import com.ttchain.githubusers.base.BaseFragment
import com.ttchain.githubusers.tools.SMSContentObserver
import kotlinx.android.synthetic.main.sms_receipt.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SmsReceiptFragment : BaseFragment(), SMSContentObserver.MessageListener {

    companion object {
        fun newInstance() = SmsReceiptFragment()
    }

    override val layoutId: Int
        get() = R.layout.sms_receipt

    private val viewModel by sharedViewModel<SmsViewModel>()
    private var smsContentObserver: SMSContentObserver? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        smsContentObserver?.unRegister()
    }

    override fun initView() {
        viewModel.receiptText = ""
        startButton.setOnClickListener {
            RxPermissions(requireActivity())
                .request(
                    Manifest.permission.READ_SMS
                )
                .toMain()
                .subscribe { granted ->
                    if (granted) {
                        val bankAccountNumber = editTextBankAccountNo.text.toString()
                        viewModel.bankAccountNumber = bankAccountNumber
                        if (bankAccountNumber.isBlank()) {
                            childFragmentManager.showSendToast(
                                false,
                                getString(R.string.error),
                                getString(R.string.empty_error)
                            )
                        } else {
                            registerSMSObserver()
                        }
                    } else {
                        startSettingsActivity()
                    }
                }
        }
    }

    private fun initData() {
        viewModel.apply {
            receiptResult.observe(viewLifecycleOwner) {
                when {
                    receiptText.isBlank() -> {
                        receiptText.plus(it)
                    }
                    else -> {
                        receiptText.plus("\n$it")
                    }
                }
            }
            receiptError.observe(viewLifecycleOwner) {
                childFragmentManager.showSendToast(
                    false, getString(R.string.error), it
                )
            }
        }
    }

    override fun onReceived(message: String?) {
        viewModel.receipt(message.orEmpty())
    }

    /**
     * 註冊簡訊觀察器
     */
    private fun registerSMSObserver() {
        smsContentObserver = SMSContentObserver(requireContext(), Handler())
        smsContentObserver?.register(this)
    }
}