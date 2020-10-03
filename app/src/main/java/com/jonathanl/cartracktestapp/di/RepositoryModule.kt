package com.jonathanl.cartracktestapp.di

import android.content.Context
import androidx.room.Room
import com.jonathanl.cartracktestapp.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LoginDatabase {
        return Room.databaseBuilder(context, LoginDatabase::class.java, "Login Database")
            .build()
    }

    @Provides
    fun provideLoginDAO(database: LoginDatabase): LoginDAO {
        return database.getLoginDAO()
    }

    @Provides
    fun provideRepository(loginDAO: LoginDAO): Repository {
        return Repository(loginDAO)
    }
}