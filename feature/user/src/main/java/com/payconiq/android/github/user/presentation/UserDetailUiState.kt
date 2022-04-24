package com.payconiq.android.github.user.presentation

import com.payconiq.android.github.core.model.UserDetail

/**
 * State management for screens with user detail.
 */
sealed interface UserDetailUiState {

    /**
     * Loading the user's details.
     */
    object LoadingUser : UserDetailUiState

    /**
     * Successfully loaded the user's details.
     *
     * @property user the loaded user.
     */
    data class LoadedUser(val user: UserDetail) : UserDetailUiState

    /**
     * An error occurred.
     *
     * @property error the error thrown.
     */
    data class Error(val error: Throwable) : UserDetailUiState
}
