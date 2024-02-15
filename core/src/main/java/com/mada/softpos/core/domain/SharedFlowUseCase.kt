package com.mada.softpos.core.domain

import kotlinx.coroutines.flow.*

abstract class SharedFlowUseCase<in Input, Output> {

    operator fun invoke(parameters: Input): Flow<Output> {
        return execute(parameters)
    }

    abstract fun execute(parameter: Input): Flow<Output>
}