package com.payconiq.android.github.remote.source

import com.payconiq.android.github.remote.api.ApiFactory
import com.payconiq.android.github.remote.api.service.UserService
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
import java.util.UUID
import kotlin.random.Random
import kotlin.random.nextUInt

class UserRemoteSourceTest {

    private lateinit var remoteSource: UserRemoteSource

    private val mockServer = MockWebServer()

    @Before
    fun setup() {
        MockKAnnotations.init()

        mockServer.start()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val okHttpClient = ApiFactory.makeOkHttpClient()

        val service = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UserService::class.java)

        remoteSource = UserRemoteSource(service)
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }

    @Test
    fun `check that searchUsers returns the correct user list`() = runBlocking {
        val query = UUID.randomUUID().toString()
        val page = Random.nextUInt().toInt()
        val expectedPath = "/search/users?q=$query&page=$page&per_page=30"

        val expectedUsername = "JakeWharton"
        val expectedSize = 5

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("json/user/search_users.json"))

        mockServer.enqueue(mockResponse)

        val result = remoteSource.searchUsers(query, page)

        Assert.assertEquals(expectedSize, result.size)
        Assert.assertEquals(expectedUsername, result[0].username)

        val request = mockServer.takeRequest()

        Assert.assertEquals(expectedPath, request.path)
    }

    @Test
    fun `check that getUsersFollowing returns the correct user list`() = runBlocking {
        val expectedUsername = "JackWaiting"

        val page = Random.nextUInt().toInt()
        val expectedPath = "/users/$expectedUsername/followers?page=$page&per_page=30"
        val expectedSize = 5

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("json/user/users_following.json"))

        mockServer.enqueue(mockResponse)

        val result = remoteSource.getUsersFollowers(expectedUsername, page)

        Assert.assertEquals(expectedSize, result.size)
        Assert.assertEquals(expectedUsername, result[0].username)

        val request = mockServer.takeRequest()

        Assert.assertEquals(expectedPath, request.path)
    }

    @Test
    fun `check that getUsersFollowers returns the correct user list`() = runBlocking {
        val expectedUsername = "highgic"

        val page = Random.nextUInt().toInt()
        val expectedPath = "/users/$expectedUsername/followers?page=$page&per_page=30"
        val expectedSize = 5

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("json/user/users_followers.json"))

        mockServer.enqueue(mockResponse)

        val result = remoteSource.getUsersFollowers(expectedUsername, page)

        Assert.assertEquals(expectedSize, result.size)
        Assert.assertEquals(expectedUsername, result[0].username)

        val request = mockServer.takeRequest()

        Assert.assertEquals(expectedPath, request.path)
    }

    @Test
    fun `check that getUser returns the correct user list`() = runBlocking {
        val expectedUsername = "acefalobi"

        val expectedPath = "/users/$expectedUsername"

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("json/user/user_detail.json"))

        mockServer.enqueue(mockResponse)

        val result = remoteSource.getUser(expectedUsername)

        Assert.assertEquals(expectedUsername, result.username)

        val request = mockServer.takeRequest()

        Assert.assertEquals(expectedPath, request.path)
    }
}
