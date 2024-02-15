package com.mada.softpos.core_di.modules

import com.mada.features.auth.login.LoginVM
import com.mada.features.history_feature.main.HistoryVM
import com.mada.features.main.MainVM
import com.mada.features.management_feature.main.ManagementVM
import com.mada.features.more_feature.main.MoreVM
import com.mada.softpos.core.di.AssistedViewModelFactory
import com.mada.softpos.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap
import com.mada.features.transactions_feature.main.TransactionsVM
import com.mada.softpos.core.di.MavericksViewModelComponent

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginVM::class)
    fun loginViewModelFactory(factory: LoginVM.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsVM::class)
    fun transactionsViewModelFactory(factory: TransactionsVM.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(HistoryVM::class)
    fun historyViewModelFactory(factory: HistoryVM.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(ManagementVM::class)
    fun managementViewModelFactory(factory: ManagementVM.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(MoreVM::class)
    fun moreViewModelFactory(factory: MoreVM.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(MainVM::class)
    fun mainViewModelFactory(factory: MainVM.Factory): AssistedViewModelFactory<*, *>
}