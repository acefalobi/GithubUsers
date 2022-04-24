package com.payconiq.android.github.core.model.base

/**
 * Base interface for basic user models.
 */
interface IUser {
    /**
     * The user's Github username
     */
    val username: String

    /**
     * The url for the user's avatar.
     */
    val avatarUrl: String

    /**
     * The URL of the user's Github account
     */
    val profileUrl: String
}
