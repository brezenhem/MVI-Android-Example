package com.mada.softpos.core.domain

abstract class CallableUseCase<in Input, Output> {

    operator fun invoke(parameters: Input): CallableResult<Output> {
        return try {
            execute(parameters)
        } catch (e: Exception) {
            CallableResult.Failure(e)
        }
    }

    abstract fun execute(parameter: Input): CallableResult<Output>
}
