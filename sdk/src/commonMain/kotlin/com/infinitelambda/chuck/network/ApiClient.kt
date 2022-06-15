package com.infinitelambda.chuck.network

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult
import io.ktor.client.*
import io.ktor.client.request.*

internal interface ApiClient {

    suspend fun getRandomJoke(category: JokeCategory? = null): Result<Joke>

    suspend fun getJokeCategories(): Result<List<JokeCategory>>

    suspend fun findJoke(query: String): Result<JokeSearchResult>

    companion object {

        fun create(
            isLoggingEnabled: Boolean,
            requestTimeout: Long,
            connectTimeout: Long
        ): ApiClient {
            val engine = HttpEngineFactory.createHttpEngine()
            val client = HttpClientFactory.createHttpClient(
                engine,
                isLoggingEnabled,
                requestTimeout,
                connectTimeout
            )
            return ApiClientImpl(client)
        }

    }
}

internal class ApiClientImpl(private val httpClient: HttpClient) : ApiClient {

    override suspend fun getRandomJoke(category: JokeCategory?): Result<Joke> =
        kotlin.runCatching {
            httpClient.get("$BASE_URL/random") {
                category?.let { parameter("category", it.name.lowercase()) }
            }
        }

    override suspend fun getJokeCategories(): Result<List<JokeCategory>> =
        kotlin.runCatching { httpClient.get("$BASE_URL/categories") }

    override suspend fun findJoke(query: String): Result<JokeSearchResult> =
        kotlin.runCatching {
            httpClient.get("$BASE_URL/search") {
                parameter("query", query)
            }
        }

    companion object {
        private const val BASE_URL = "https://api.chucknorris.io/jokes"
    }

}