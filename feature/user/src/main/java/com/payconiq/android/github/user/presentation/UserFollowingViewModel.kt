package com.payconiq.android.github.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payconiq.android.github.common.util.runOnIO
import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * View Model for managing logic on the "Following" screen.
 */
@HiltViewModel
class UserFollowingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.LoadingUsers)

    /**
     * UI state flow.
     */
    val uiState: StateFlow<UserListUiState> = _uiState

    /**
     * Exposed user list to keep during lifecycle changes.
     */
    var loadedUsers = mutableListOf<User>()

    private var currentPage = 0

    /**
     * Load the next page of the user's following or the first page if no users have been loaded.
     */
    fun loadUserFollowing(username: String) {
        if (currentPage > 0) {
            _uiState.value = UserListUiState.LoadingMoreUsers
        } else {
            _uiState.value = UserListUiState.LoadingUsers
        }
        currentPage++

        viewModelScope.runOnIO({
            val nextUsers = userRepository.getFollowing(username, currentPage)
            loadedUsers = loadedUsers.union(nextUsers).toMutableList()
            if (nextUsers.size < 30) {
                currentPage--
                _uiState.value = UserListUiState.FinishedLoadingUsers
            } else {
                _uiState.value = UserListUiState.LoadedUsers
            }
        }, {
            currentPage--
            _uiState.value = UserListUiState.Error(it)
        })
    }
}
