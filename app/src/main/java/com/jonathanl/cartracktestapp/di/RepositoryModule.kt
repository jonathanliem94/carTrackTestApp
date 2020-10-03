package com.jonathanl.cartracktestapp.di

import android.content.Context
import androidx.room.Room
import com.jonathanl.cartracktestapp.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideLoginDatabase(@ApplicationContext context: Context): LoginDatabase {
        return Room.databaseBuilder(context, LoginDatabase::class.java, "Login Database")
            .build()
    }

    @Provides
    fun provideLoginDAO(database: LoginDatabase): LoginDAO {
        return database.getLoginDAO()
    }

    @Provides
    fun provideLoginRepository(loginDAO: LoginDAO): LoginRepository {
        return LoginRepository(loginDAO)
    }

    @Provides
    fun provideNetworkRepository(service: NetworkService): NetworkRepository {
        return NetworkRepository(service)
    }

    @Provides
    fun provideNetworkService(): NetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NetworkService::class.java)
    }
}