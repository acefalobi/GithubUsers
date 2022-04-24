package com.payconiq.android.github.user.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.payconiq.android.github.common.R
import com.payconiq.android.github.common.ui.theme.GithubUsersTheme

@Composable
fun UserSearch(
    modifier: Modifier = Modifier,
    performQuery: (String) -> Unit = {}
) {
    Box(modifier) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant)
                .padding(30.dp, 20.dp, 30.dp, 30.dp)
        ) {
            var text by rememberSaveable { mutableStateOf("") }

            val focusRequester = remember { FocusRequester() }
            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                text,
                { text = it },
                Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = { Text(stringResource(R.string.prompt_search), fontSize = 16.sp) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions {
                    if (text.isNotEmpty()) {
                        keyboardController?.hide()
                        performQuery(text.trim())
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
    // TODO: Add functionality for recent searches as a new LazyColumn (or maybe Backdrop) here.
}

@Preview
@Composable
fun UserSearchPreview() {
    GithubUsersTheme {
        UserSearch()
    }
}
