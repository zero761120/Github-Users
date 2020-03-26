package com.ttchain.githubusers.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ttchain.githubusers.view.BottomTabState
import com.ttchain.githubusers.ui.main.MainFragment
import com.ttchain.githubusers.ui.user.UserFragment

class MainPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            BottomTabState.MAIN.position -> MainFragment.newInstance()
            BottomTabState.USER.position -> UserFragment.newInstance()
            else -> MainFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
