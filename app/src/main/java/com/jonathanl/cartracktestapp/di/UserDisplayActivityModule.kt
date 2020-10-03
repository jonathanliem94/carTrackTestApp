package com.jonathanl.cartracktestapp.di

import com.jonathanl.cartracktestapp.data.Repository
import com.jonathanl.cartracktestapp.ui.userdisplay.UserDisplayViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object UserDisplayActivityModule {

    @Provides
    fun provideUserDisplayViewModel(repository: Repository): UserDisplayViewModel {
        return UserDisplayViewModel(repository)
    }


}