package com.payconiq.android.github.user.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Domain
import androidx.compose.material.icons.rounded.PersonPinCircle
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.payconiq.android.github.common.R
import com.payconiq.android.github.common.ui.theme.GithubUsersTheme
import com.payconiq.android.github.common.ui.theme.buttonTheme
import com.payconiq.android.github.common.util.extractMessage
import com.payconiq.android.github.common.util.openUrl
import com.payconiq.android.github.common.util.shareText
import com.payconiq.android.github.core.model.UserDetail
import com.payconiq.android.github.user.presentation.UserDetailUiState
import com.payconiq.android.github.user.presentation.UserDetailViewModel
import com.payconiq.android.github.user.util.closeBottomSheet
import kotlinx.coroutines.launch

@Composable
fun UserDetailsSheet(
    scaffoldState: BottomSheetScaffoldState,
    username: State<String>,
    navController: NavHostController = rememberNavController()
) {
    val selectedUsername by username

    val viewModel = hiltViewModel<UserDetailViewModel>().apply {
        if (selectedUsername.isNotEmpty()) loadUserDetails(selectedUsername)
    }
    UserDetailsWrapper(scaffoldState, viewModel) {
        navController.navigate("users/following_followers/$it")
    }
}

/**
 * Exists so UI state changes don't recompose [UserDetailsSheet] and
 * re-trigger [UserDetailViewModel.loadUserDetails]].
 */
@Composable
fun UserDetailsWrapper(
    scaffoldState: BottomSheetScaffoldState,
    viewModel: UserDetailViewModel,
    openFollowersFollowing: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    UserDetails(scaffoldState, uiState, openFollowersFollowing)
}

@Composable
fun UserDetails(
    scaffoldState: BottomSheetScaffoldState,
    uiState: UserDetailUiState = UserDetailUiState.LoadingUser,
    openFollowersFollowing: (String) -> Unit = {},
) {
    val errorMessage = if (uiState is UserDetailUiState.Error) {
        uiState.error.extractMessage()
    } else null

    Box {
        if (errorMessage != null) {
            LaunchedEffect(uiState) {
                launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        errorMessage,
                        duration = SnackbarDuration.Short
                    )
                    scaffoldState.bottomSheetState.collapse()
                }
            }
            LaunchedEffect(uiState) {
                launch {
                    scaffoldState.bottomSheetState.collapse()
                }
            }
        }

        if (uiState is UserDetailUiState.LoadedUser) {
            UserDetailColumn(
                scaffoldState,
                uiState.user,
                false,
                openFollowersFollowing = openFollowersFollowing
            )
        } else {
            UserDetailColumn(scaffoldState, UserDetail.placeHolderObject, true)
        }
    }
}

@Composable
fun UserDetailColumn(
    scaffoldState: BottomSheetScaffoldState,
    user: UserDetail,
    isPlaceHolder: Boolean = false,
    openFollowersFollowing: (String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(40.dp, 80.dp)
            .verticalScroll(rememberScrollState()),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        AsyncImage(
            ImageRequest.Builder(context)
                .data(user.avatarUrl)
                .scale(Scale.FILL)
                .crossfade(true)
                .build(),
            user.username,
            Modifier
                .clip(RoundedCornerShape(100.dp))
                .height(100.dp)
                .width(100.dp)
                .placeholder(visible = isPlaceHolder, highlight = PlaceholderHighlight.shimmer()),
            ColorPainter(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        user.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.placeholder(
                    visible = isPlaceHolder,
                    highlight = PlaceholderHighlight.shimmer()
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))
        }

        Text(
            text = "@${user.username}",
            modifier = Modifier
                .alpha(if (isPlaceHolder) 1F else .6F)
                .placeholder(
                    visible = isPlaceHolder,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(20.dp))

        user.bio?.let {
            Text(
                text = it,
                modifier = Modifier
                    .alpha(if (isPlaceHolder) 1F else .8F)
                    .placeholder(
                        visible = isPlaceHolder,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            user.location?.let {
                if (!isPlaceHolder) Icon(
                    Icons.Rounded.PersonPinCircle,
                    it,
                    Modifier,
                    MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    it,
                    Modifier
                        .alpha(if (isPlaceHolder) 1F else .6F)
                        .placeholder(
                            visible = isPlaceHolder,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (user.company != null) Spacer(modifier = Modifier.width(14.dp))
            }

            user.company?.let {
                if (!isPlaceHolder) Icon(
                    Icons.Rounded.Domain,
                    it,
                    Modifier,
                    MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    it,
                    Modifier
                        .alpha(if (isPlaceHolder) 1F else .6F)
                        .placeholder(
                            visible = isPlaceHolder,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserStat(stringResource(R.string.label_followers), user.noOfFollowers, isPlaceHolder) {
                closeBottomSheet(scope, scaffoldState)
                openFollowersFollowing(user.username)
            }
            Spacer(Modifier.width(16.dp))
            UserStat(stringResource(R.string.label_following), user.noOfFollowing, isPlaceHolder) {
                closeBottomSheet(scope, scaffoldState)
                openFollowersFollowing(user.username)
            }
            Spacer(Modifier.width(16.dp))
            UserStat(stringResource(R.string.label_repos), user.noOfRepos, isPlaceHolder) {
                context.openUrl("https://github.com/${user.username}?tab=repositories")
            }
            Spacer(Modifier.width(16.dp))
            UserStat(stringResource(R.string.label_gists), user.noOfGists, isPlaceHolder) {
                context.openUrl("https://gist.github.com/${user.username}")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        FilledTonalButton(
            { context.openUrl(user.profileUrl) },
            Modifier.placeholder(isPlaceHolder, highlight = PlaceholderHighlight.shimmer()),
            colors = buttonTheme(),
        ) {
            Text(
                stringResource(R.string.action_open_in_github),
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.h5
            )
        }

        Spacer(Modifier.height(10.dp))

        val shareText = stringResource(
            R.string.template_share_profile,
            user.name ?: user.username,
            user.profileUrl
        )
        FilledTonalButton(
            { context.shareText(shareText) },
            Modifier.placeholder(isPlaceHolder, highlight = PlaceholderHighlight.shimmer()),
            colors = buttonTheme(),
        ) {
            Text(
                stringResource(R.string.action_share_profile),
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.h5
            )
        }

        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun UserStat(label: String, value: Int, isPlaceHolder: Boolean = false, onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            Modifier
                .alpha(if (isPlaceHolder) 1F else .6F)
                .placeholder(
                    visible = isPlaceHolder,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            style = MaterialTheme.typography.h6,
        )
        Spacer(Modifier.height(4.dp))
        TextButton(
            onClick,
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface)
        ) {
            Text(
                "%,d".format(value),
                Modifier
                    .placeholder(
                        visible = isPlaceHolder,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                style = MaterialTheme.typography.h2,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailPreview() {
    GithubUsersTheme {
        val scaffoldState = rememberBottomSheetScaffoldState()
        UserDetails(scaffoldState, UserDetailUiState.LoadedUser(UserDetail.placeHolderObject))
    }
}
