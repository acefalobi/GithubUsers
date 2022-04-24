package com.payconiq.android.github.user.repository

import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.core.model.UserDetail
import com.payconiq.android.github.core.remote.IUserRemoteSource
import com.payconiq.android.github.user.makeRandomUserDetail
import com.payconiq.android.github.user.makeRandomUserList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.UUID
import kotlin.random.Random
import kotlin.random.nextUInt

class UserRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var remoteSource: IUserRemoteSource

    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        repository = UserRepository(remoteSource)
    }

    @Test
    fun `check that getPopularUsers returns the correct users`() = runBlocking {
        val expectedResult = makeRandomUserList(10)
        val expectedPage = Random.nextUInt().toInt()
        val expectedQuery = "repos:%3E42+followers:%3E1000"

        stubSearchUsers(expectedQuery, expectedPage, expectedResult)

        val result = repository.getPopularUsers(expectedPage)

        coVerify(exactly = 1) {
            remoteSource.searchUsers(expectedQuery, expectedPage)
        }

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `check that getUserDetails returns the correct users`() = runBlocking {
        val expectedUsername = UUID.randomUUID().toString()
        val expectedResult = makeRandomUserDetail(expectedUsername)

        stubGetUser(expectedUsername, expectedResult)

        val result = repository.getUserDetails(expectedUsername)

        coVerify(exactly = 1) {
            remoteSource.getUser(expectedUsername)
        }

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `check that getFollowers returns the correct users`() = runBlocking {
        val expectedResult = makeRandomUserList(10)
        val expectedPage = Random.nextUInt().toInt()
        val expectedUsername = UUID.randomUUID().toString()

        stubGetUsersFollowers(expectedUsername, expectedPage, expectedResult)

        val result = repository.getFollowers(expectedUsername, expectedPage)

        coVerify(exactly = 1) {
            remoteSource.getUsersFollowers(expectedUsername, expectedPage)
        }

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `check that getFollowing returns the correct users`() = runBlocking {
        val expectedResult = makeRandomUserList(10)
        val expectedPage = Random.nextUInt().toInt()
        val expectedUsername = UUID.randomUUID().toString()

        stubGetUsersFollowing(expectedUsername, expectedPage, expectedResult)

        val result = repository.getFollowing(expectedUsername, expectedPage)

        coVerify(exactly = 1) {
            remoteSource.getUsersFollowing(expectedUsername, expectedPage)
        }

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `check that searchUsers returns the correct users`() = runBlocking {
        val expectedResult = makeRandomUserList(10)
        val expectedPage = Random.nextUInt().toInt()
        val expectedQuery = UUID.randomUUID().toString()

        stubSearchUsers(expectedQuery, expectedPage, expectedResult)

        val result = repository.searchUsers(expectedQuery, expectedPage)

        coVerify(exactly = 1) {
            remoteSource.searchUsers(expectedQuery, expectedPage)
        }

        Assert.assertEquals(expectedResult, result)
    }

    private fun stubGetUsersFollowers(username: String, page: Int, users: List<User>) {
        coEvery { remoteSource.getUsersFollowers(username, page) } returns users
    }

    private fun stubGetUsersFollowing(username: String, page: Int, users: List<User>) {
        coEvery { remoteSource.getUsersFollowing(username, page) } returns users
    }

    private fun stubSearchUsers(query: String, page: Int, users: List<User>) {
        coEvery { remoteSource.searchUsers(query, page) } returns users
    }

    private fun stubGetUser(username: String, user: UserDetail) {
        coEvery { remoteSource.getUser(username) } returns user
    }
}
