package com.payconiq.android.github.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiFactoryTest {

    private lateinit var service: TestService

    private val mockServer = MockWebServer()

    @Before
    fun setup() {
        MockKAnnotations.init()

        mockServer.start()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val okHttpClient =
            ApiFactory.makeOkHttpClient(ApiFactory.makeAuthorizationInterceptor())

        service = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TestService::class.java)
    }

    @After
    fun cleanup() {
        mockServer.shutdown()
    }

    @Test
    fun `check that the authorization header is sent`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("[]")

        mockServer.enqueue(mockResponse)

        service.test()

        val request = mockServer.takeRequest()

        Assert.assertTrue(request.getHeader("Authorization")?.contains("Basic ") == true)
    }
}
