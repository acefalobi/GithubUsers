package com.payconiq.android.github.common.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Run a suspend function on the IO thread.
 *
 * @param T the return parameter of the action.
 * @param action the suspend function to invoke.
 * @param onError the action to take when an exception is encountered.
 *
 * @return the output of the suspend function [action].
 */
fun <T> CoroutineScope.runOnIO(
    action: suspend () -> T,
    onError: (Throwable) -> Unit,
) {
    launch {
        withContext(Dispatchers.IO) {
            try {
                action()
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
}
