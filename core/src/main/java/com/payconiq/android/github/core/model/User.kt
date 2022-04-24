package com.payconiq.android.github.core.model

import com.payconiq.android.github.core.model.base.IUser

/**
 * Core data class for [IUser].
 */
data class User(
    override val username: String,
    override val profileUrl: String,
    override val avatarUrl: String
) : IUser {
    companion object {
        val placeHolderObject = User("", "", "")
    }
}
