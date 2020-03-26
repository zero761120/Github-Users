package com.ttchain.githubusers.ui

import android.os.Bundle
import com.ttchain.githubusers.R
import com.ttchain.githubusers.base.BaseActivity
import com.ttchain.githubusers.view.BottomTabState
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.main_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        view_pager.apply {
            setSwipeable(false)
            offscreenPageLimit = mainPagerAdapter.count
            adapter = mainPagerAdapter
        }
        bottomTabView.setOnItemClickListener { bottomTabStatus ->
            view_pager.currentItem = when (bottomTabStatus) {
                BottomTabState.MAIN -> 0
                BottomTabState.USER -> 1
            }
            bottomTabView.setTabPosition(bottomTabStatus)
        }
        bottomTabView.setItemClick(BottomTabState.MAIN)
    }
}