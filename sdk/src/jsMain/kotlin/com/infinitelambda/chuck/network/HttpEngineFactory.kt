package com.infinitelambda.chuck.network

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

internal actual object HttpEngineFactory {

    actual fun createHttpEngine(): HttpClientEngine = Js.create()

}