package com.mada.softpos.core_di.modules

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        AuthApiModule::class,
        MockApiModule::class
        // other api modules
    ]
)
interface NetworkAggregatorModule