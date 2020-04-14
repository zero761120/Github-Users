package com.ttchain.githubusers.net

import com.ttchain.githubusers.data.UserData
import com.ttchain.githubusers.data.UserListData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("users")
    fun getUserList(
        @Query("since") since: Int
    ): Single<List<UserListData>>

    @GET("users/{username}")
    fun getUserData(
        @Path("username") userName: String
    ): Single<UserData>
}