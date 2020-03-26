package com.ttchain.githubusers.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ttchain.githubusers.R
import com.ttchain.githubusers.base.BaseActivity
import com.ttchain.githubusers.data.UserData
import com.ttchain.githubusers.ui.user.UserViewModel
import kotlinx.android.synthetic.main.view_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserActivity : BaseActivity() {

    companion object {

        private const val USER_NAME = "USER_NAME"

        fun launch(activity: Activity, userName: String) {
            activity.startActivity(Intent(activity, UserActivity::class.java).apply {
                putExtra(USER_NAME, userName)
            })
        }
    }

    override val layoutId: Int
        get() = R.layout.view_user

    private val viewModel by viewModel<UserViewModel>()
    private val userName: String
        get() = intent.getStringExtra(USER_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        imageViewCancel.visibility = View.VISIBLE
        imageViewCancel.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        viewModel.apply {
            userResult.observe(this@UserActivity) {
                setData(it)
            }
            getUser(userName)
        }
    }

    private fun setData(userData: UserData) {
        Glide.with(imageViewAvatar)
            .load(userData.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(imageViewAvatar)
        textViewName.text = userData.name
        textViewBio.text = userData.bio
        textViewId.text = userData.login
        textViewLocation.text = userData.location
        textViewBlog.text = userData.blog
    }
}