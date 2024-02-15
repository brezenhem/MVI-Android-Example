package com.mada.softpos.core_di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mada.data.network.interceptor.NetworkInterceptor
import com.mada.data.network.service.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
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
class AuthApiModule {

    companion object {
        private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MiB
        private const val MAX_STALE = 60 * 60 * 24 * 7 //  Tolerate 1-week stale
        private const val CONNECT_TIMEOUT: Long = 60
        private const val READ_TIMEOUT: Long = 60
        private const val WRITE_TIMEOUT: Long = 60
    }

    @Provides
    @Singleton
    @Named("auth_service")
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.setLenient().create()
    }

    @Provides
    @Singleton
    @Named("auth_service")
    fun provideOkHttpCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, CACHE_SIZE.toLong())
    }

    @Provides
    @Singleton
    @Named("auth_service")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    @Named("auth_service")
    fun provideCacheOverrideInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            chain.proceed(chain.request())
                .newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-age=$MAX_STALE")
                .build()
        }
    }

    @Provides
    @Singleton
    @Named("auth_service")
    fun provideServerHttpClient(
        @Named("auth_service") loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor,
        @Named("auth_service") cache: Cache,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("auth_service")
    fun provideRetrofitServer(
        @Named("auth_service") gson: Gson,
        @Named("auth_service") okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://pos-host.isaac-sandbox.tribepayments.com/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(@Named("auth_service") retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}