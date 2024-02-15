package com.mada.softpos.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

abstract class FlowUseCase<in Input, Output>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: Input): Flow<SuspendResult<Output>> {
        return execute(parameters).flowOn(coroutineDispatcher)
    }

    abstract fun execute(parameter: Input): Flow<SuspendResult<Output>>
}

