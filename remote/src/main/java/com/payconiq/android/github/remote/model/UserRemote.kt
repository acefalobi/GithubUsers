package com.payconiq.android.github.remote.model

import androidx.annotation.Keep
import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.core.model.base.IUser
import com.squareup.moshi.Json

/**
 * Remote-layer data class for [IUser].
 */
@Keep
data class UserRemote(
    @Json(name = "login")
    override val username: String,

    @Json(name = "html_url")
    override val profileUrl: String,

    @Json(name = "avatar_url")
    override val avatarUrl: String,
) : IUser {
    /**
     * Maps the remote model to the core model.
     */
    fun toCoreModel(): User = User(username, profileUrl, avatarUrl)
}
