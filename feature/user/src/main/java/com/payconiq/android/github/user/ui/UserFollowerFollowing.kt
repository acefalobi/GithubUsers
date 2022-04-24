package com.payconiq.android.github.user.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.payconiq.android.github.common.R
import com.payconiq.android.github.user.presentation.UserFollowersViewModel
import com.payconiq.android.github.user.presentation.UserFollowingViewModel
import com.payconiq.android.github.user.presentation.UserListUiState

@Composable
fun UserFollowerFollowing(
    scaffoldState: BottomSheetScaffoldState,
    username: String,
    modifier: Modifier = Modifier,
    onUserDetailOpen: (String) -> Unit = {},
) {
    var currentPosition by remember { mutableStateOf(0) }
    val titles = listOf(
        stringResource(R.string.label_followers),
        stringResource(R.string.label_following)
    )

    Column {
        TabRow(
            currentPosition,
            backgroundColor = MaterialTheme.colors.primaryVariant,
            contentColor = MaterialTheme.colors.onSecondary,
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = currentPosition == index,
                    onClick = { currentPosition = index }
                )
            }
        }
        when (currentPosition) {
            0 -> {
                val viewModel = hiltViewModel<UserFollowersViewModel>().apply {
                    if (uiState.value is UserListUiState.LoadingUsers) loadUserFollowers(
                        username
                    )
                }
                UserFollowers(
                    scaffoldState,
                    modifier,
                    viewModel,
                    { viewModel.loadedUsers },
                    { viewModel.loadUserFollowers(username) },
                    onUserDetailOpen
                )
            }
            1 -> {
                val viewModel = hiltViewModel<UserFollowingViewModel>().apply {
                    if (uiState.value is UserListUiState.LoadingUsers) loadUserFollowing(
                        username
                    )
                }
                UserFollowing(
                    scaffoldState,
                    modifier,
                    viewModel,
                    { viewModel.loadedUsers },
                    { viewModel.loadUserFollowing(username) },
                    onUserDetailOpen
                )
            }
        }
    }
}
