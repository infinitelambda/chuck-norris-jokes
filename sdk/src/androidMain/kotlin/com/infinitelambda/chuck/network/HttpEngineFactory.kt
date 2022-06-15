package com.infinitelambda.chuck.network

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

internal actual object HttpEngineFactory {

    actual fun createHttpEngine(): HttpClientEngine = OkHttp.create()

}