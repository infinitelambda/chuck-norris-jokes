package com.infinitelambda.chuck.util

import io.ktor.client.features.logging.*
import platform.Foundation.NSLog

internal actual object HttpLogger: Logger {

    override fun log(message: String) {
        NSLog("$TAG: $message")
    }

}