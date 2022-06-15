package com.infinitelambda.chuck

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult
import com.infinitelambda.chuck.network.ApiClient

@JokeClientDsl
fun JokeClient(block: JokeClientConfig.() -> Unit): JokeClient {
    val config = JokeClientConfig().apply(block)
    return JokeClientImpl(ApiClient.create(config.isLoggingEnabled, config.requestTimeout, config.connectTimeout))
}

class JokeClientConfig {

    var isLoggingEnabled: Boolean = false

    var requestTimeout: Long = 10000L

    var connectTimeout: Long = 10000L

}

interface JokeClient {

    suspend fun getRandomJoke(category: JokeCategory? = null): Result<Joke>

    suspend fun getJokeCategories(): Result<List<JokeCategory>>

    suspend fun findJoke(query: String): Result<JokeSearchResult>

}

internal class JokeClientImpl (private val apiClient: ApiClient) :
    JokeClient {

    override suspend fun getRandomJoke(category: JokeCategory?): Result<Joke> =
        apiClient.getRandomJoke(category)

    override suspend fun getJokeCategories(): Result<List<JokeCategory>> =
        apiClient.getJokeCategories()

    override suspend fun findJoke(query: String): Result<JokeSearchResult> =
        apiClient.findJoke(query)
}