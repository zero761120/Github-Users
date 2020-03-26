package com.ttchain.githubusers.repository

import com.ttchain.githubusers.data.UserData
import com.ttchain.githubusers.data.UserListData
import com.ttchain.githubusers.net.GitHubApi
import io.reactivex.Single

class GitHubRepository(
    private val api: GitHubApi
) : BaseRepository() {

    fun getUserList(since: Int): Single<List<UserListData>> {
        return getApi(api.getUserList(since))
    }

    fun getUserData(userName: String): Single<UserData> {
        return getApi(api.getUserData(userName))
    }
}