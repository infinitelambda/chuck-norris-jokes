package com.infinitelambda.chuck.util

import android.util.Log
import io.ktor.client.plugins.logging.*

internal actual object HttpLogger : Logger {

    override fun log(message: String) {
        Log.d(TAG, message)
    }

}