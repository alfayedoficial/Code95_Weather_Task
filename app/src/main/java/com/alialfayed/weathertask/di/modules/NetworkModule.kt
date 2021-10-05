package com.alialfayed.weathertask.di.modules

import android.content.Context
import com.alialfayed.weathertask.BuildConfig
import com.alialfayed.weathertask.domain.api.ApiGooglePlaces
import com.alialfayed.weathertask.core.utils.AppConstants.BASE_URL_RETROFIT_API
import com.alialfayed.weathertask.core.utils.AppConstants.REQUEST_TIME_OUT
import com.alialfayed.weathertask.core.utils.AppPreferences
import com.alialfayed.weathertask.domain.api.ApiService
import com.alialfayed.weathertask.core.utils.AppConstants.BASE_URL_GOOGLE_PLACES_API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 9/27/2021 - 1:09 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHeadersInterceptor(appPreferences: AppPreferences, @ApplicationContext context: Context) =
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer ${appPreferences.userToken ?: ""}")
                    .build()
            )
        }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        logging: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headersInterceptor)
                .addNetworkInterceptor(logging)
                .addInterceptor(ChuckInterceptor(context))
                .build()
        } else {
            OkHttpClient.Builder()
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headersInterceptor)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls() // To allow sending null values
            .create()
    }

    @Provides
    @Singleton
    fun provideHomeServices(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return getDynamicRetrofitClient(gson , okHttpClient ,BASE_URL_RETROFIT_API).create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeGooglePlaces(gson: Gson, okHttpClient: OkHttpClient): ApiGooglePlaces {
        return getDynamicRetrofitClient(gson , okHttpClient ,BASE_URL_GOOGLE_PLACES_API).create(ApiGooglePlaces::class.java)
    }

    private fun getDynamicRetrofitClient(
        gson: Gson,
        client: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}