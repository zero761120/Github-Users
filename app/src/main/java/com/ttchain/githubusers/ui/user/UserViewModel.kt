package com.ttchain.githubusers.ui.user

import androidx.lifecycle.MutableLiveData
import com.ttchain.githubusers.base.BaseViewModel
import com.ttchain.githubusers.data.UserData
import com.ttchain.githubusers.data.UserListData
import com.ttchain.githubusers.repository.GitHubRepository

class UserViewModel(
    private val gitHubRepository: GitHubRepository
) : BaseViewModel() {

    val userResult = MutableLiveData<UserData>()

    fun getUser(userName: String) {
        add(
            gitHubRepository.getUserData(userName)
                .subscribe({
                    userResult.value = it
                }, {
                })
        )
    }
}
