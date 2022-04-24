package com.payconiq.android.github.core.model

import com.payconiq.android.github.core.model.base.IUserDetail

/**
 * Core data class for [IUserDetail].
 */
data class UserDetail(
    override val name: String?,
    override val username: String,
    override val profileUrl: String,
    override val avatarUrl: String,
    override val company: String?,
    override val location: String?,
    override val bio: String?,
    override val noOfRepos: Int,
    override val noOfGists: Int,
    override val noOfFollowers: Int,
    override val noOfFollowing: Int
) : IUserDetail {
    companion object {
        val placeHolderObject = UserDetail(
            null,
            "xxxxxxxxx",
            "",
            "",
            "xxxxxxxxxxxxxxx",
            "xxxxxxxxxxxx",
            null,
            10000,
            10000,
            10000,
            10000,
        )
    }
}
