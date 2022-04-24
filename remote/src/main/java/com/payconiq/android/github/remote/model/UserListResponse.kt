package com.payconiq.android.github.remote.model

import androidx.annotation.Keep

@Keep
data class UserListResponse(val items: List<UserRemote>)
