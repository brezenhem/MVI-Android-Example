package com.mada.softpos.core_di.modules

import com.mada.data.network.utils.SessionManager
import com.mada.domain.load_management.LoadManagementUseCase
import com.mada.domain.load_management.LoadManagementUseCaseImpl
import com.mada.data.repository.impl.AuthRepository
import com.mada.data.repository.impl.LocalDataRepository
import com.mada.data.repository.impl.TransactionsRepository
import com.mada.domain.load_more.LoadMoreUseCase
import com.mada.domain.load_more.LoadMoreUseCaseImpl
import com.mada.domain.load_transactions.LoadTransactionsUseCase
import com.mada.domain.load_transactions.LoadTransactionsUseCaseImpl
import com.mada.domain.create_transaction.CreateTransactionUseCase
import com.mada.domain.create_transaction.CreateTransactionUseCaseImpl
import com.mada.domain.load_transactions_history.LoadTransactionsHistoryUseCase
import com.mada.domain.load_transactions_history.LoadTransactionsHistoryUseCaseImpl
import com.mada.domain.login_user.UserLoginUseCase
import com.mada.domain.login_user.UserLoginUseCaseImpl
import com.mada.domain.user_logged_in.UserLoggedInUseCase
import com.mada.domain.user_logged_in.UserLoggedInUseCaseImpl
import com.mada.domain.user_logout.UserLogoutUseCase
import com.mada.domain.user_logout.UserLogoutUseCaseImpl
import com.mada.domain.user_session.SessionLifecycleUseCase
import com.mada.domain.user_session.SessionLifecycleUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideCreateTransactionsUseCase(
        coroutineDispatcher: CoroutineDispatcher,
        transactionsRepository: TransactionsRepository,
    ): CreateTransactionUseCase {
        return CreateTransactionUseCaseImpl(coroutineDispatcher, transactionsRepository)
    }

    @Provides
    @Singleton
    fun provideLoadMoreUseCase(
        coroutineDispatcher: CoroutineDispatcher,
    ): LoadMoreUseCase {
        return LoadMoreUseCaseImpl(coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideLoadTransactionsUseCase(
        coroutineDispatcher: CoroutineDispatcher,
    ): LoadTransactionsUseCase {
        return LoadTransactionsUseCaseImpl(coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideLoadTransactionsHistoryUseCase(
        repository: TransactionsRepository
    ): LoadTransactionsHistoryUseCase {
        return LoadTransactionsHistoryUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideUserLoggedInUseCase(
        repository: LocalDataRepository
    ): UserLoggedInUseCase {
        return UserLoggedInUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideSessionLifecycleUseCase(
        authRepository: AuthRepository,
        repository: LocalDataRepository,
        sessionManager: SessionManager
    ): SessionLifecycleUseCase {
        return SessionLifecycleUseCaseImpl(authRepository, repository, sessionManager)
    }

    @Provides
    @Singleton
    fun provideLoadManagementUseCase(
        coroutineDispatcher: CoroutineDispatcher,
    ): LoadManagementUseCase {
        return LoadManagementUseCaseImpl(coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideUserLoginUseCase(
        coroutineDispatcher: CoroutineDispatcher,
        authRepository: AuthRepository,
    ): UserLoginUseCase {
        return UserLoginUseCaseImpl(coroutineDispatcher, authRepository)
    }

    @Provides
    @Singleton
    fun provideUserLogoutUseCase(
        sessionManager: SessionManager,
    ): UserLogoutUseCase {
        return UserLogoutUseCaseImpl(sessionManager)
    }
}