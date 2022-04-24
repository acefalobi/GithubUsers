package com.payconiq.android.github.user

import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.core.model.UserDetail
import java.util.UUID
import kotlin.random.Random
import kotlin.random.nextUInt

private fun makeRandomUser() = User(
    UUID.randomUUID().toString(),
    UUID.randomUUID().toString(),
    UUID.randomUUID().toString()
)

fun makeRandomUserList(size: Int) = MutableList(size) { makeRandomUser() }

fun makeRandomUserDetail(id: String = UUID.randomUUID().toString()) = UserDetail(
    id,
    UUID.randomUUID().toString(),
    UUID.randomUUID().toString(),
    UUID.randomUUID().toString(),
    UUID.randomUUID().toString(),
    UUID.randomUUID().toString(),
    UUID.randomUUID().toString(),
    Random.nextUInt().toInt(),
    Random.nextUInt().toInt(),
    Random.nextUInt().toInt(),
    Random.nextUInt().toInt(),
)
