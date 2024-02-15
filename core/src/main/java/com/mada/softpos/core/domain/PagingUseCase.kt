package com.mada.softpos.core.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.*

abstract class PagingUseCase<Input, Output : Any> {

    abstract fun execute(): Flow<PagingData<Output>>

    abstract fun Input.mapper(): Output
}

