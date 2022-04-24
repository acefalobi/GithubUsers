package com.payconiq.android.github.core.remote

import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.core.model.UserDetail

/**
 * Interface for accessing user information from remote sources (Rest APIs, etc.).
 */
interface IUserRemoteSource {
    /**
     * Search for users that match a given [query].
     *
     * @param page the page from the search result to fetch from.
     *
     * @see <a href="https://docs.github.com/search-github/searching-on-github/searching-users]">
     *     Github docs</a> for more info on how to build the query strings
     */
    suspend fun searchUsers(query: String, page: Int): List<User>

    /**
     * Get the users that [username] follows.
     *
     * @param page the page from the result to fetch from.
     */
    suspend fun getUsersFollowing(username: String, page: Int): List<User>

    /**
     * Get the users that follow [username].
     *
     * @param page the page from the result to fetch from.
     */
    suspend fun getUsersFollowers(username: String, page: Int): List<User>

    /**
     * Get a user with a particular [username].
     */
    suspend fun getUser(username: String): UserDetail
}
