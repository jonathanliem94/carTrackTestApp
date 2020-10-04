package com.jonathanl.cartracktestapp.di

import com.jonathanl.cartracktestapp.data.NetworkRepository
import com.jonathanl.cartracktestapp.data.NetworkService
import com.jonathanl.cartracktestapp.ui.userdisplay.UserDisplayViewAdapter
import com.jonathanl.cartracktestapp.ui.userdisplay.UserDisplayViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object UserDisplayActivityModule {

    @Provides
    fun provideUserDisplayViewModel(networkRepository: NetworkRepository): UserDisplayViewModel {
        return UserDisplayViewModel(networkRepository)
    }

    @Provides
    fun provideUserDisplayViewAdapter(): UserDisplayViewAdapter {
        return UserDisplayViewAdapter()
    }

}