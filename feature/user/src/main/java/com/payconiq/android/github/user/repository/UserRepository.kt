package com.payconiq.android.github.user.repository

import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.core.model.UserDetail
import com.payconiq.android.github.core.remote.IUserRemoteSource
import javax.inject.Inject

/**
 * For accessing user data and sources.
 */
class UserRepository @Inject constructor(private val userRemoteSource: IUserRemoteSource) {

    /**
     * Get the popular users from Github (users with over 20 repos and 1000 followers).
     */
    suspend fun getPopularUsers(page: Int): List<User> {
        return userRemoteSource.searchUsers("repos:%3E42+followers:%3E1000", page)
    }

    /**
     * Get the user details for user with [username].
     */
    suspend fun getUserDetails(username: String): UserDetail {
        return userRemoteSource.getUser(username)
    }

    /**
     * Get all followers of user with [username].
     */
    suspend fun getFollowers(username: String, page: Int): List<User> {
        return userRemoteSource.getUsersFollowers(username, page)
    }

    /**
     * Get all users that user with [username] follows.
     */
    suspend fun getFollowing(username: String, page: Int): List<User> {
        return userRemoteSource.getUsersFollowing(username, page)
    }

    /**
     * Search for users using a [query].
     */
    suspend fun searchUsers(query: String, page: Int): List<User> {
        return userRemoteSource.searchUsers(query, page)
    }
}
