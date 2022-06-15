package com.infinitelambda.chuck.network

import io.ktor.client.engine.*

internal expect object HttpEngineFactory {

    fun createHttpEngine(): HttpClientEngine

}