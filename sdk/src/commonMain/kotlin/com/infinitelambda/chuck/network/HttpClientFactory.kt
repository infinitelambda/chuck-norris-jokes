package com.infinitelambda.chuck.network

import com.infinitelambda.chuck.util.HttpLogger
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

internal object HttpClientFactory {

    fun createHttpClient(engine: HttpClientEngine, isLoggingEnabled: Boolean, requestTimeout: Long, connectTimeout: Long) =
        HttpClient(engine) {
            defaultRequest {
                header(HttpHeaders.Accept, ContentType.Application.Json)
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
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