package com.payconiq.android.github.remote.api.service

import com.payconiq.android.github.remote.model.UserDetailRemote
import com.payconiq.android.github.remote.model.UserListResponse
import com.payconiq.android.github.remote.model.UserRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface for Github user endpoints.
 */
interface UserService {
    /**
     * REST API endpoint to get a user from Github.
     */
    @GET("users/{username}")
    suspend fun getUserByUsername(
        @Path("username") username: String
    ): UserDetailRemote

    /**
     * REST API endpoint to get a user's followers from Github.
     */
    @GET("users/{username}/followers")
    suspend fun getUsersFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): List<UserRemote>

    /**
     * REST API endpoint to get a user's following from Github.
     */
    @GET("users/{username}/following")
    suspend fun getUsersFollowing(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): List<UserRemote>

    /**
     * REST API endpoint to search for user's on Github.
     */
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): UserListResponse
}
