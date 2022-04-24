package com.payconiq.android.github.user.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.payconiq.android.github.common.R
import com.payconiq.android.github.common.ui.theme.GithubUsersTheme
import com.payconiq.android.github.common.util.extractMessage
import com.payconiq.android.github.common.util.openUrl
import com.payconiq.android.github.core.model.User
import com.payconiq.android.github.user.presentation.UserListUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun UserList(
    scaffoldState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    uiState: UserListUiState = UserListUiState.LoadingUsers,
    getUserList: () -> List<User> = { emptyList() },
    loadMoreUsers: () -> Unit = {},
    onUserDetailOpen: (String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    val errorMessage = if (uiState is UserListUiState.Error) {
        uiState.error.extractMessage()
    } else null

    val retryString = stringResource(R.string.action_retry)

    val listState = rememberLazyListState()

    if (uiState is UserListUiState.FinishedLoadingUsers && getUserList().isEmpty()) {
        EmptyList(
            modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.surface)
        )
    } else {
        LazyColumn(
            modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.surface),
            listState,
            PaddingValues(10.dp)
        ) {

            if (errorMessage != null) {
                scope.launch {
                    val snackbarData = scaffoldState.snackbarHostState.showSnackbar(
                        errorMessage,
                        retryString,
                        SnackbarDuration.Indefinite
                    )
                    if (snackbarData == SnackbarResult.ActionPerformed) {
                        loadMoreUsers()
                    }
                }
            }

            if (uiState is UserListUiState.LoadingUsers || (uiState is UserListUiState.Error && getUserList().isEmpty())) {
                items(20, { it }) {
                    UserItemCard(User.placeHolderObject, true)
                }
            } else {
                getUserList().forEach { user ->
                    item(user.username) {
                        UserItemCard(user) {
                            onUserDetailOpen(it.username)
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
                }
                if (uiState != UserListUiState.FinishedLoadingUsers) {
                    items(3, { it }) {
                        UserItemCard(User.placeHolderObject, true)
                    }
                }
                if (uiState == UserListUiState.LoadedUsers && getUserList().size <= 30) {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                }
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.key }
            .distinctUntilChanged()
            .filter { it == 0 || it == 1 || it == 2 }
            .collect {
                if (uiState != UserListUiState.FinishedLoadingUsers && uiState != UserListUiState.LoadingMoreUsers && getUserList().size >= 30) {
                    loadMoreUsers()
                }
            }
    }
}

@Composable
fun EmptyList(modifier: Modifier) {
    Column(
        modifier,
        Arrangement.Center,
    ) {
        Text(
            ":(",
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onSecondary,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(20.dp))
        Text(
            stringResource(R.string.error_no_users),
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun UserItemCard(
    user: User,
    isPlaceHolder: Boolean = false,
    onCardClick: (User) -> Unit = {},
) {
    Card(
        { onCardClick(user) },
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .placeholder(
                visible = isPlaceHolder,
                highlight = PlaceholderHighlight.shimmer()
            ),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 0.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current

            AsyncImage(
                ImageRequest.Builder(context)
                    .data(user.avatarUrl)
                    .size(200)
                    .scale(Scale.FILL)
                    .build(),
                user.username,
                Modifier
                    .clip(MaterialTheme.shapes.large)
                    .height(50.dp)
                    .width(50.dp),
                ColorPainter(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Text(
                user.username,
                Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                { context.openUrl(user.profileUrl) },
            ) {
                Icon(
                    Icons.Rounded.OpenInNew,
                    stringResource(R.string.action_open_in_github),
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}

@Preview
@Composable
fun UserListPreview() {
    GithubUsersTheme {
        UserList(
            rememberBottomSheetScaffoldState(),
            uiState = UserListUiState.LoadedUsers,
            getUserList = {
                listOf(
                    User("acefalobi1", "", ""),
                    User("acefalobi2", "", ""),
                    User("acefalobi3", "", ""),
                    User("acefalobi4", "", ""),
                    User("acefalobi5", "", ""),
                    User("acefalobi6", "", ""),
                    User("acefalobi7", "", ""),
                )
            }
        )
    }
}
