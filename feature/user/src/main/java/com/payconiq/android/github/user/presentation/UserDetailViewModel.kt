package com.payconiq.android.github.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payconiq.android.github.common.util.runOnIO
import com.payconiq.android.github.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * View Model for managing logic on the "User Details" screen.
 */
@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.LoadingUser)

    private var lastLoadedUsername = ""

    /**
     * UI state flow.
     */
    val uiState: StateFlow<UserDetailUiState> = _uiState

    /**
     * Load the details of the user with [username].
     */
    fun loadUserDetails(username: String) {
        if (username.isNotEmpty() && username != lastLoadedUsername) {
            _uiState.value = UserDetailUiState.LoadingUser

            viewModelScope.runOnIO({
                val user = userRepository.getUserDetails(username)
                lastLoadedUsername = user.username
                _uiState.value = UserDetailUiState.LoadedUser(user)
            }, {
                _uiState.value = UserDetailUiState.Error(it)
            })
        }
    }
}
