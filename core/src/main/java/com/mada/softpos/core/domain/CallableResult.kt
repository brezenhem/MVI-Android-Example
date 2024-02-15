package com.mada.softpos.core.domain

sealed class CallableResult<out R> {
    data class Success<out T>(val data: T) : CallableResult<T>()
    data class Failure(val exception: Throwable) : CallableResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$exception]"
        }
    }
}
