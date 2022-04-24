package com.payconiq.android.github.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.payconiq.android.github.common.R
import com.payconiq.android.github.core.exception.AppException

/**
 * Extract the message from a generic exception.
 *
 * @return the extracted exception message.
 */
@Composable
fun Throwable.extractMessage() = if (this is AppException) {
    getMessage()
} else {
    localizedMessage ?: stringResource(R.string.error_generic)
}

/**
 * Extract the message from an app exception.
 *
 * @return the extracted message or [R.string.error_generic]
 */
@Composable
private fun AppException.getMessage() =
    messageResId?.let { stringResource(it) }
        ?: message
        ?: stringResource(R.string.error_generic)
