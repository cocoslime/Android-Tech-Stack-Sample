package com.cocoslime.samples.apps.androidtechstack.data.di

import com.cocoslime.samples.apps.androidtechstack.data.service.GithubService
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
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val TIME_OUT_SECONDS = 10L

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

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
    fun provideGithubService(
        httpClientBuilder: OkHttpClient.Builder,
        networkJson: Json,
    ): GithubService {
        val httpClient = httpClientBuilder.build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(httpClient)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create(GithubService::class.java)
    }
}
