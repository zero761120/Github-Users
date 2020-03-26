package com.ttchain.githubusers.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.observe
import com.ttchain.githubusers.R
import com.ttchain.githubusers.base.BaseFragment
import com.ttchain.githubusers.ui.UserActivity
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override val layoutId: Int
        get() = R.layout.main_fragment

    private val viewModel by viewModel<MainViewModel>()
    private val userDataAdapter = UserDataAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    override fun initView() {
        userDataAdapter.apply {
            setOnLoadMoreListener {
                viewModel.source += 50
                viewModel.getUserList(viewModel.source)
            }
            setOnItemClickListener {
                UserActivity.launch(requireActivity(), it.login.orEmpty())
            }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = userDataAdapter
        }
    }

    private fun initData() {
        viewModel.apply {
            userListResult.observe(viewLifecycleOwner) {
                userDataAdapter.updateData(it)
            }
            getUserList(source)
        }
    }
}
