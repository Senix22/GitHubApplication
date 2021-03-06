package com.senix22.secondchanceapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.senix22.secondchanceapp.data.AcceptHeaderInterceptor
import com.senix22.secondchanceapp.data.GithubAuthService
import com.senix22.secondchanceapp.data.GithubDataService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(private val context: Context) {

    companion object {
        private const val AUTH_BASE_URL = "https://github.com"
        private const val DATA_BASE_URL = "https://api.github.com"
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGithubDataService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): GithubDataService {
        return Retrofit.Builder().baseUrl(DATA_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
            .create(GithubDataService::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubAuthService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): GithubAuthService {
        return Retrofit.Builder().baseUrl(AUTH_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
            .create(GithubAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AcceptHeaderInterceptor())
            .build()
    }


    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(retrofit: Retrofit): AcceptHeaderInterceptor {
        return retrofit.create(AcceptHeaderInterceptor::class.java)
    }

}