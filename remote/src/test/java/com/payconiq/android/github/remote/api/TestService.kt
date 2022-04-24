package com.payconiq.android.github.remote.api

import retrofit2.http.GET

/**
 * Retrofit interface for test endpoints
 */
interface TestService {

    /**
     * Test endpoint
     *
     * @return response object returned by endpoint.
     */
    @GET("test")
    suspend fun test(): List<String>
}
