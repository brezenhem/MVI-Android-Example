package com.mada.softpos.core_di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mada.data.network.service.MockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MockApiModule {

    companion object {
        private const val CONNECT_TIMEOUT: Long = 60
        private const val READ_TIMEOUT: Long = 60
        private const val WRITE_TIMEOUT: Long = 60
    }

    @Provides
    @Singleton
    @Named("mock_service")
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.setLenient().create()
    }

    @Provides
    @Singleton
    @Named("mock_service")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    @Named("mock_service")
    fun provideServerHttpClient(@Named("mock_service") loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("mock_service")
    fun provideRetrofitServer(
        @Named("mock_service") gson: Gson,
        @Named("mock_service") okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.punkapi.com/v2/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMockApi(@Named("mock_service") retrofit: Retrofit): MockApi {
        return retrofit.create(MockApi::class.java)
    }
}