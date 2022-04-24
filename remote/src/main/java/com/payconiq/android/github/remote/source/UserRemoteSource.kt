package com.payconiq.android.github.remote.source

import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.core.model.UserDetail
import com.payconiq.android.github.core.remote.IUserRemoteSource
import com.payconiq.android.github.remote.api.ApiFactory
import com.payconiq.android.github.remote.api.service.UserService
import com.payconiq.android.github.remote.util.processApiCall
import javax.inject.Inject

/**
 * Remote source accessor for Github user data.
 */
class UserRemoteSource @Inject constructor() : IUserRemoteSource {
    private var userService: UserService = ApiFactory.userService

    constructor(userService: UserService) : this() {
        this.userService = userService
    }

    override suspend fun searchUsers(query: String, page: Int): List<User> = processApiCall {
        userService.searchUsers(query, page)
    }.items.map { it.toCoreModel() }

    override suspend fun getUsersFollowing(username: String, page: Int): List<User> =
        processApiCall {
            userService.getUsersFollowing(username, page)
        }.map { it.toCoreModel() }

    override suspend fun getUsersFollowers(username: String, page: Int): List<User> =
        processApiCall {
            userService.getUsersFollowers(username, page)
        }.map { it.toCoreModel() }

    override suspend fun getUser(username: String): UserDetail = processApiCall {
        userService.getUserByUsername(username)
    }.toCoreModel()
}
