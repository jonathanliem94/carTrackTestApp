package com.jonathanl.cartracktestapp.di

import com.jonathanl.cartracktestapp.data.LoginDataSource
import com.jonathanl.cartracktestapp.data.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideDataSource(): LoginDataSource {
        return LoginDataSource()
    }

    @Provides
    fun provideLoginRepository(dataSource: LoginDataSource): LoginRepository {
        return LoginRepository(dataSource)
    }

}