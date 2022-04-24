package com.payconiq.android.github.remote.di

import com.payconiq.android.github.core.remote.IUserRemoteSource
import com.payconiq.android.github.remote.source.UserRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("KDocMissingDocumentation")
@Module
@InstallIn(SingletonComponent::class)
interface RemoteSourceModule {

    @get:[Binds Singleton]
    val UserRemoteSource.userRemoteSource: IUserRemoteSource
}
