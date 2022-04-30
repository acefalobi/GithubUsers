package com.payconiq.android.github.remote.model

import androidx.annotation.Keep
import com.payconiq.android.github.core.model.UserDetail
import com.payconiq.android.github.core.model.base.IUserDetail
import com.squareup.moshi.Json

/**
 * Remote-layer data class for [IUserDetail].
 */
@Keep
data class UserDetailRemote(
    override val name: String?,

    @Json(name = "login")
    override val username: String,

    @Json(name = "html_url")
    override val profileUrl: String,

    @Json(name = "avatar_url")
    override val avatarUrl: String,

    override val company: String?,
    override val location: String?,
    override val bio: String?,

    @Json(name = "public_repos")
    override val noOfRepos: Int,

    @Json(name = "public_gists")
    override val noOfGists: Int,

    @Json(name = "followers")
    override val noOfFollowers: Int,

    @Json(name = "following")
    override val noOfFollowing: Int,
) : IUserDetail {

    /**
     * Maps the remote model to the core model.
     */
    fun toCoreModel(): UserDetail = UserDetail(
        name,
        username,
        profileUrl,
        avatarUrl,
        company,
        location,
        bio,
        noOfRepos,
        noOfGists,
        noOfFollowers,
        noOfFollowing
    )
}
