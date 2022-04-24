package com.payconiq.android.github.user.ui

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.user.presentation.UserFollowingViewModel

@Composable
fun UserFollowing(
    scaffoldState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    viewModel: UserFollowingViewModel,
    getUserList: () -> List<User> = { emptyList() },
    loadMoreUsers: () -> Unit = {},
    onUserDetailOpen: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    UserList(scaffoldState, modifier, uiState, getUserList, loadMoreUsers, onUserDetailOpen)
}
