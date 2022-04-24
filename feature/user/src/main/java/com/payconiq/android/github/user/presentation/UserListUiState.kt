package com.payconiq.android.github.user.presentation

/**
 * State management for screens with user lists.
 */
sealed interface UserListUiState {

    /**
     * Loading the first page of users.
     */
    object LoadingUsers : UserListUiState

    /**
     * Loading another page of users.
     */
    object LoadingMoreUsers : UserListUiState

    /**
     * Successfully loaded the users.
     */
    object LoadedUsers : UserListUiState

    /**
     * Successfully loaded all the users in the entire result set.
     */
    object FinishedLoadingUsers : UserListUiState

    /**
     * An error occurred.
     *
     * @property error the error thrown.
     */
    data class Error(val error: Throwable) : UserListUiState
}
