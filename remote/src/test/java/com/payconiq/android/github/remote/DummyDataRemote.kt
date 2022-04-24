package com.payconiq.android.github.remote

import java.io.File

/**
 * Load json string from path.
 *
 * @param path path of JSON file
 * @return the json string
 */
fun Any.getJson(path: String): String {
    val uri = this.javaClass.classLoader!!.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}
