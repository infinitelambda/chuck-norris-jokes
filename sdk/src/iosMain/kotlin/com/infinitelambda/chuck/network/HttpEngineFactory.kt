package com.infinitelambda.chuck.network

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

internal actual object HttpEngineFactory {

    actual fun createHttpEngine(): HttpClientEngine = Darwin.create()

}