package com.ttchain.githubusers.ui.user

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ttchain.githubusers.R
import com.ttchain.githubusers.base.BaseFragment
import com.ttchain.githubusers.data.UserData
import kotlinx.android.synthetic.main.view_user.*
import androidx.lifecycle.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    override val layoutId: Int
        get() = R.layout.view_user

    private val viewModel by viewModel<UserViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun initView() {
        val userName = "zero761120"
        viewModel.apply {
            userResult.observe(viewLifecycleOwner) {
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