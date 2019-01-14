package com.example.ura.myapplication.data.exchange.api

import com.example.ura.myapplication.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .callTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PRIVATE_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePrivateCurrencyApi(retrofit: Retrofit): PrivateExchangeApi {
        return retrofit.create(PrivateExchangeApi::class.java)
    }

    companion object {
        private val PRIVATE_API_BASE_URL = BuildConfig.PRIVATE_API_BASE_URL
        private val TIMEOUT = 35000
    }
}
