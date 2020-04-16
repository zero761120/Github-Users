package com.ttchain.githubusers.koin

import com.ttchain.githubusers.repository.GitHubRepository
import com.ttchain.githubusers.repository.SmsRepository
import com.ttchain.githubusers.ui.main.MainViewModel
import com.ttchain.githubusers.ui.sms.SmsViewModel
import com.ttchain.githubusers.ui.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { SmsViewModel(get(), get()) }
}

val repositoryModule = module {
    single { GitHubRepository(get()) }
    single { SmsRepository(get()) }
}

val appModule = listOf(
    viewModelModule,
    repositoryModule,
    networkModule
)