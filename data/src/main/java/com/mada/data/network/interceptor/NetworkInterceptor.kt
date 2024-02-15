package com.mada.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor() :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            throw SocketTimeoutException("Connection timeout")
        }
        if (!response.isSuccessful)
            handleResponseCode(response)

        return response
    }

    private fun handleResponseCode(response: Response) {
        //  TODO: Implement it in future
    }
}