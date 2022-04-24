package com.payconiq.android.github.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.payconiq.android.github.common.R
import com.payconiq.android.github.common.ui.theme.GithubUsersTheme
import com.payconiq.android.github.common.ui.theme.SnackbarActionColor
import com.payconiq.android.github.common.ui.theme.topAppBarTheme
import com.payconiq.android.github.user.ui.Page
import com.payconiq.android.github.user.ui.UserDetailsSheet
import com.payconiq.android.github.user.ui.UserList
import com.payconiq.android.github.user.ui.UserNavGraph
import com.payconiq.android.github.user.util.closeBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GithubUsers()
        }
    }
}

@Composable
fun GithubUsers() {
    ProvideWindowInsets {
        GithubUsersTheme {
            val navController = rememberNavController()
            val scaffoldState = rememberBottomSheetScaffoldState()
            val title = remember { mutableStateOf("") }
            val selectedUser = remember { mutableStateOf("") }
            val scope = rememberCoroutineScope()
            BottomSheetScaffold(
                {
                    UserDetailsSheet(scaffoldState, selectedUser, navController)
                },
                Modifier.pointerInput(Unit) {
                    detectTapGestures {
                        closeBottomSheet(scope, scaffoldState)
                    }
                },
                scaffoldState,
                { AppBar(navController, title) },
                {
                    SnackbarHost(it) { data ->
                        Snackbar(
                            actionColor = SnackbarActionColor,
                            snackbarData = data
                        )
                    }
                },
                sheetPeekHeight = 0.dp,
                sheetShape = RoundedCornerShape(30.dp, 30.dp),
            ) {
                Column {
                    UserNavGraph(
                        scaffoldState,
                        Modifier
                            .padding(it)
                            .navigationBarsPadding(),
                        navController,
                        {
                            selectedUser.value = it
                        },
                        { title.value = it }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProvideWindowInsets {
        GithubUsersTheme {
            val navController = rememberNavController()
            val scaffoldState = rememberBottomSheetScaffoldState()
            val title = stringResource(R.string.label_popular)
            BottomSheetScaffold(
                { },
                topBar = {
                    AppBar(
                        navController,
                        remember { mutableStateOf(title) }
                    )
                }
            ) {
                Column {
                    UserList(scaffoldState)
                }
            }
        }
    }
}

@Composable
fun AppBar(navController: NavHostController, title: State<String>) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination?.route

    val appBarTitle by title

    // / Because the graph isn't set when the App Bar is first created.
    val graph = try {
        navController.graph
    } catch (e: IllegalStateException) {
        null
    }

    CenterAlignedTopAppBar(
        {
            Text(
                appBarTitle,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSecondary
            )
        },
        Modifier.statusBarsPadding(),
        {
            if (graph != null && currentDestination != graph.startDestinationRoute) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        stringResource(R.string.action_back),
                        tint = MaterialTheme.colors.onSecondary
                    )
                }
            }
        },
        {
            if (currentDestination == Page.POPULAR.route) {
                IconButton(
                    onClick = {
                        navController.navigate(Page.SEARCH.route) {
                            restoreState = true
                            popUpTo(Page.POPULAR.route) {
                                saveState = true
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Rounded.Search,
                        stringResource(R.string.action_search),
                        tint = MaterialTheme.colors.onSecondary
                    )
                }
            }
        },
        colors = topAppBarTheme(),
    )
}
