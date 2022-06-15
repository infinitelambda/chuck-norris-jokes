package com.infinitelambda.chuck.network

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*

internal actual object HttpEngineFactory {

    actual fun createHttpEngine(): HttpClientEngine = Ios.create()

}