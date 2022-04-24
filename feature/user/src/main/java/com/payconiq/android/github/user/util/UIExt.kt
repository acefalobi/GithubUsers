package com.payconiq.android.github.user.util

import androidx.compose.material.BottomSheetScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Asynchronously close an open bottom sheet.
 *
 * @param scope the coroutine scope to run on.
 * @param scaffoldState the scaffold state holding the bottom sheet.
 */
fun closeBottomSheet(scope: CoroutineScope, scaffoldState: BottomSheetScaffoldState) {
    scope.launch {
        if (scaffoldState.bottomSheetState.isExpanded) {
            scaffoldState.bottomSheetState.collapse()
        }
    }
}
