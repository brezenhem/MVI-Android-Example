package com.mada.softpos.core_di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [AppSubcomponents::class]
)
internal interface AppComponent