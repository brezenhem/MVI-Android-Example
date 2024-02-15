package com.mada.softpos.core_di

import com.mada.softpos.core_di.components.DataAggregatorComponent
import com.mada.softpos.core_di.components.DomainAggregatorComponent
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        DataAggregatorComponent::class,
        DomainAggregatorComponent::class
    ]
)
class AppSubcomponents