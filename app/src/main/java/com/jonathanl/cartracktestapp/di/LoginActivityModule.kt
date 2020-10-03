package com.jonathanl.cartracktestapp.di

import com.jonathanl.cartracktestapp.data.LoginRepository
import com.jonathanl.cartracktestapp.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object LoginActivityModule {

    @Provides
    fun provideLoginViewModel(loginRepository: LoginRepository): LoginViewModel {
        return LoginViewModel(loginRepository)
    }

}