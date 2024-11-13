package com.cocoslime.data.di

import com.cocoslime.data.service.GithubService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
    }

    @Provides
    fun provideService(httpClientBuilder: OkHttpClient.Builder): GithubService {
        val httpClient = httpClientBuilder
            .build()

        val converterFactory = Json {
            ignoreUnknownKeys = true
        }
            .asConverterFactory("application/json; charset=UTF8".toMediaType())

        val retrofit =
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(httpClient)
                .addConverterFactory(converterFactory)
                .build()

        return retrofit
            .create(GithubService::class.java)
    }


    companion object {
        const val TIME_OUT_SECONDS = 10L
    }
}