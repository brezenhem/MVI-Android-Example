package com.mada.softpos.core_di.components

import com.mada.softpos.core_di.modules.*
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        UseCaseModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class DomainAggregatorComponent