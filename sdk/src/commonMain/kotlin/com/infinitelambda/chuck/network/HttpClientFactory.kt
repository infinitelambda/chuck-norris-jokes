package com.infinitelambda.chuck.network

import com.infinitelambda.chuck.util.HttpLogger
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal object HttpClientFactory {

    fun createHttpClient(
        engine: HttpClientEngine,
        isLoggingEnabled: Boolean,
        requestTimeout: Long,
        connectTimeout: Long
    ) =
        HttpClient(engine) {
            defaultRequest {
                header(HttpHeaders.Accept, ContentType.Application.Json)
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = HttpLogger
                level = if (isLoggingEnabled) LogLevel.BODY else LogLevel.NONE
            }

            install(HttpTimeout) {
                requestTimeoutMillis = requestTimeout
                connectTimeoutMillis = connectTimeout
            }
        }

}