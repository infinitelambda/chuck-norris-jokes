package com.infinitelambda.chuck.util

import io.ktor.client.features.logging.*

internal actual object HttpLogger : Logger {

    override fun log(message: String) {
        console.log("$TAG: $message")
    }

}