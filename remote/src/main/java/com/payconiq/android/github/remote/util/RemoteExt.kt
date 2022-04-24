package com.payconiq.android.github.remote.util

import androidx.annotation.StringRes
import com.payconiq.android.github.core.exception.AppException
import com.payconiq.android.github.remote.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

/**
 * Invokes retrofit coroutine endpoint and processes exceptions accordingly.
 *
 * @param T The data type of the endpoint result data object.
 * @param call The function that makes the request.
 *
 * @return The data object.
 */
suspend fun <T> processApiCall(call: suspend () -> T): T = try {
    val response = call.invoke()

    response
} catch (e: Throwable) {
    Timber.e(e)
    throw AppException(
        extractErrorMessage(e) {
            try {
                val response = response()?.errorBody()?.string()
                val remoteResponse = moshi.adapter<Map<String, Any>>().fromJson(response ?: "")
                if (remoteResponse?.containsKey("message") == true)
                    throw AppException(remoteResponse["message"].toString())
                R.string.error_request
            } catch (e: JSONException) {
                Timber.e(e)
                R.string.error_request
            } catch (e: IOException) {
                Timber.e(e)
                R.string.error_request
            }
        },
        e
    )
}

@StringRes
private fun extractErrorMessage(
    throwable: Throwable,
    httpErrorCatcher: HttpException.() -> Int
): Int {
    return when (throwable) {
        is ConnectException,
        is UnknownHostException,
        is SocketTimeoutException,
        is UnknownServiceException,
        is ProtocolException -> R.string.error_connection
        is HttpException -> throwable.httpErrorCatcher()
        else -> R.string.error_request
    }
}
