package com.mada.softpos.core.domain
/**
 * Sealed class that implements different error types for UI scope
 */

sealed class ErrorResult : Throwable() {
    object RuntimeError : ErrorResult()
    object ConnectionError : ErrorResult()
    data class ServerError(val errorMessage: String) : ErrorResult()
}

fun Throwable.asResult(): ErrorResult = this as ErrorResult