package com.mada.data.network.utils

import com.google.gson.Gson
import com.mada.data.network.model.response.ErrorResponse
import com.mada.softpos.core.domain.ErrorResult
import com.mada.softpos.core.domain.SuspendResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Coroutine wrapper to execute a suspend function with a given dispatcher
 * and typed error
 */
suspend fun <T> suspendApiCall(
    dispatcher: CoroutineDispatcher,
    call: suspend () -> T
) = try {
    withContext(dispatcher) {
        SuspendResult.Success(call.invoke())
    }
} catch (e: Throwable) {
    SuspendResult.Failure(convertErrorBody(e))
}

fun convertErrorBody(e: Throwable): ErrorResult {
    when (e) {
        is HttpException -> {
            val errorBody = e.response()?.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                return try {
                    val data = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    ErrorResult.ServerError(errorMessage = data.error)
                } catch (e: Exception) {
                    ErrorResult.ServerError(errorMessage = "Unknown server error")
                }
            }
            return ErrorResult.ServerError("Unknown server error")
        }
        is UnknownHostException ->
            return ErrorResult.ConnectionError
        else -> {
            return ErrorResult.RuntimeError
        }
    }
}