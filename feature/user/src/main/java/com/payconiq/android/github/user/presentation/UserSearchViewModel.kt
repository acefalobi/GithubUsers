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
 * View Model for managing logic on the "Search" screen.
 */
@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.LoadingUsers)

    /**
     * UI state flow.
     */
    val uiState: StateFlow<UserListUiState> = _uiState

    /**
     * The last searched term.
     */
    private var searchQuery = ""

    /**
     * Exposed user list to keep during lifecycle changes.
     */
    var loadedUsers = mutableListOf<User>()

    private var currentPage = 0

    /**
     * Search for users that match a [query].
     * Load the next page of search results or the first page if this is a different query string.
     */
    fun searchUser(query: String) {
        if (query != searchQuery) currentPage = 0

        if (currentPage > 0) {
            _uiState.value = UserListUiState.LoadingMoreUsers
        } else {
            _uiState.value = UserListUiState.LoadingUsers
        }
        currentPage++

        viewModelScope.runOnIO({
            val nextUsers = userRepository.searchUsers(query, currentPage)
            loadedUsers = loadedUsers.union(nextUsers).toMutableList()
            if (nextUsers.size < 30) {
                currentPage--
                _uiState.value = UserListUiState.FinishedLoadingUsers
            } else {
                _uiState.value = UserListUiState.LoadedUsers
            }
            searchQuery = query
        }, {
            currentPage--
            _uiState.value = UserListUiState.Error(it)
        })
    }
}
