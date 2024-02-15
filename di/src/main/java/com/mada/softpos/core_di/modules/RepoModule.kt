package com.mada.softpos.core_di.modules

import android.os.Build
import androidx.annotation.RequiresApi
import com.mada.data.database.AppDatabase
import com.mada.data.network.service.AuthApi
import com.mada.data.network.service.MockApi
import com.mada.data.preferences.Preferences
import com.mada.data.repository.impl.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
class RepoModule {

    @Provides
    @Singleton
    fun provideTransactionsRepository(
        coroutineDispatcher: CoroutineDispatcher,
        transactionApi: MockApi,
        appDatabase: AppDatabase
    ): TransactionsRepository {
        return TransactionsRepositoryImpl(coroutineDispatcher, transactionApi, appDatabase)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        coroutineDispatcher: CoroutineDispatcher,
        localDataRepository: LocalDataRepository,
        authApi: AuthApi
    ): AuthRepository {
        return AuthRepositoryMockImpl(coroutineDispatcher, localDataRepository)
    }

    @Provides
    @Singleton
    fun provideLocalDataRepository(
        preferences: Preferences
    ): LocalDataRepository {
        return LocalDataRepositoryImpl(preferences)
    }
}