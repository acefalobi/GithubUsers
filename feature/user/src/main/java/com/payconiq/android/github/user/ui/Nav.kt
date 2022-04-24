package com.payconiq.android.github.user.ui

import androidx.activity.compose.BackHandler
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.payconiq.android.github.common.R
import com.payconiq.android.github.user.presentation.PopularUsersViewModel
import com.payconiq.android.github.user.presentation.UserListUiState
import com.payconiq.android.github.user.presentation.UserSearchViewModel
import com.payconiq.android.github.user.util.closeBottomSheet

enum class Page(val route: String) {
    /**
     * User pages
     */
    POPULAR("users/popular"),
    SEARCH("users/search"),
    SEARCH_RESULT("users/search/{query}"),
    FOLLOWING_FOLLOWERS("users/following_followers/{username}"),
}

@Composable
fun UserNavGraph(
    scaffoldState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onUserDetailOpen: (String) -> Unit,
    onTitleChange: (String) -> Unit
) {

    NavHost(
        navController,
        Page.POPULAR.route,
        modifier,
    ) {
        composable(Page.POPULAR.route) {
            onTitleChange(stringResource(R.string.label_popular))

            val viewModel = hiltViewModel<PopularUsersViewModel>()

            val uiState by viewModel.uiState.collectAsState()

            UserList(
                scaffoldState,
                modifier,
                uiState,
                { viewModel.loadedUsers },
                { viewModel.loadPopularUsers() },
                onUserDetailOpen
            )
        }
        composable(Page.SEARCH.route) {
            onTitleChange("")
            UserSearch(modifier) {
                navController.navigate("users/search/$it") {
                    popUpTo("users/search")
                }
            }
        }
        composable(Page.SEARCH_RESULT.route) {
            val query = it.arguments?.getString("query") ?: ""
            onTitleChange("\"$query\"")

            val viewModel = hiltViewModel<UserSearchViewModel>().apply {
                if (uiState.value is UserListUiState.LoadingUsers) searchUser(query)
            }

            UserSearchResults(
                scaffoldState,
                modifier,
                viewModel,
                { viewModel.loadedUsers },
                { viewModel.searchUser(query) },
                onUserDetailOpen
            )
        }
        composable(Page.FOLLOWING_FOLLOWERS.route) {
            val username = it.arguments?.getString("username") ?: ""
            onTitleChange(username)
            UserFollowerFollowing(scaffoldState, username, modifier, onUserDetailOpen)
        }
    }
    val scope = rememberCoroutineScope()
    BackHandler(scaffoldState.bottomSheetState.isExpanded) {
        closeBottomSheet(scope, scaffoldState)
    }
}
