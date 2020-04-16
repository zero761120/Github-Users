package com.ttchain.githubusers.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ttchain.githubusers.addDialog
import com.ttchain.githubusers.dialog.ProgressDialog

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutId: Int
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    fun onShowLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog()
            supportFragmentManager.addDialog(progressDialog!!, "loading")
        }
    }

    fun onHideLoading() {
        progressDialog?.dismissAllowingStateLoss()
        progressDialog = null
    }
}