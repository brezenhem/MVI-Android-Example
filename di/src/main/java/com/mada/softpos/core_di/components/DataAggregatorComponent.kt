package com.mada.softpos.core_di.components

import com.mada.softpos.core_di.modules.DatabaseModule
import com.mada.softpos.core_di.modules.NetworkAggregatorModule
import com.mada.softpos.core_di.modules.RepoModule
import com.mada.softpos.core_di.modules.PreferencesModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        RepoModule::class,
        PreferencesModule::class,
        DatabaseModule::class,
        NetworkAggregatorModule::class,
    ]
)
interface DataAggregatorComponent