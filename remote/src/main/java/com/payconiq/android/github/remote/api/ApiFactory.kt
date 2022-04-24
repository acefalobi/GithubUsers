package com.payconiq.android.github.remote.api

import com.payconiq.android.github.remote.BuildConfig
import com.payconiq.android.github.remote.api.service.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Factory utility to manage Retrofit API configuration.
 */
object ApiFactory {

    private const val BASE_URL = "https://api.github.com/"

    /**
     * Holds the singleton reference for the API service for getting users.
     */
    val userService = makeService(UserService::class.java)

    /**
     * Create an implementation of the API Service with the endpoints.
     *
     * @param serviceClass The interface class containing the endpoints.
     *
     * @return An implementation of the interface class.
     */
    private fun <T> makeService(serviceClass: Class<T>): T {
        val okHttpClient = makeOkHttpClient(
            makeAuthorizationInterceptor(),
            makeLoggingInterceptor((BuildConfig.DEBUG))
        )
        return makeRetrofit(
            okHttpClient,
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        ).create(serviceClass)
    }

    private fun makeRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Creates the base [OkHttpClient] for all retrofit services.
     *
     * @param httpLoggingInterceptor an optional interceptor for logging requests and responses.
     */
    fun makeOkHttpClient(
        authorizationInterceptor: Interceptor = makeAuthorizationInterceptor(),
        httpLoggingInterceptor: HttpLoggingInterceptor? = null,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        httpLoggingInterceptor?.let { builder.addInterceptor(it) }

        return builder.build()
    }

    /**
     * Creates an [Interceptor] for adding headers for authorization to a request.
     *
     * @return the interceptor.
     */
    fun makeAuthorizationInterceptor() = Interceptor { chain ->
        val username = BuildConfig.GITHUB_USERNAME
        val password = BuildConfig.GITHUB_PASSWORD
        val newRequest: Request = if (username.isNotEmpty() && password.isNotEmpty()) {
            val credentials = Credentials.basic(username, password)
            chain.request().newBuilder().addHeader("Authorization", credentials).build()
        } else chain.request()
        chain.proceed(newRequest)
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}
