package com.payconiq.android.github.remote.util

import com.payconiq.android.github.remote.api.ApiFactory
import com.payconiq.android.github.remote.api.TestService
import com.payconiq.android.github.remote.getJson
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

class RemoteExtTest {

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
    fun `check that processApiCall returns the data when called`() = runBlocking {
        val expectedResult = listOf("test_string_1", "test_string_2")

        val expectedPath = "/test"

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("json/test/test.json"))

        mockServer.enqueue(mockResponse)

        val result = processApiCall(service::test)

        Assert.assertEquals(expectedResult, result)

        val request = mockServer.takeRequest()

        Assert.assertEquals(expectedPath, request.path)
    }

    @Test
    fun `check that processApiCall returns the right error on http failure`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setBody(getJson("json/test/test_unsuccessful.json"))

        mockServer.enqueue(mockResponse)

        try {
            processApiCall(service::test)
            Assert.fail("Expected error but got none")
        } catch (e: Throwable) {
            Assert.assertEquals("Requires authentication", e.message.toString())
        }
    }
}
