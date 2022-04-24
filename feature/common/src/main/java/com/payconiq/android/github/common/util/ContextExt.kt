package com.payconiq.android.github.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Open a new URL in whatever can open it.
 *
 * @param url the URL to parse and open.
 */
fun Context.openUrl(url: String) {
    val openUrIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    startActivity(openUrIntent)
}

/**
 * Share a piece of text to whatever accepts it.
 *
 * @param text the text string to share.
 */
fun Context.shareText(text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(shareIntent)
}
