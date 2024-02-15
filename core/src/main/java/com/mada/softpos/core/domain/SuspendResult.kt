package com.mada.softpos.core.domain

sealed class SuspendResult<out R> {
    data class Success<out T>(val data: T) : SuspendResult<T>()
    data class Failure(val exception: Throwable) : SuspendResult<Nothing>()
    object Loading : SuspendResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

fun <Input, Output> SuspendResult<Input>.asModelValue(
    mapper: (Input) -> Output
): SuspendResult<Output> {
    return when (this) {
        is SuspendResult.Success -> SuspendResult.Success(mapper(data))
        is SuspendResult.Failure -> this
        else -> throw Exception("Unsupported")
    }
}