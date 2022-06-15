package com.infinitelambda.chuck

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult
import com.infinitelambda.chuck.network.ApiClient
import com.infinitelambda.chuck.util.CompletionHandler
import com.infinitelambda.chuck.util.NativeCancellable
import com.infinitelambda.chuck.util.iosScope
import com.infinitelambda.chuck.util.withNativeCompletionHandler
import kotlin.native.concurrent.freeze

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

    fun getRandomJoke(
        category: JokeCategory? = null,
        completionHandler: CompletionHandler<Joke?>
    ): NativeCancellable

    fun getJokeCategories(completionHandler: CompletionHandler<List<JokeCategory>?>): NativeCancellable

    fun findJoke(
        query: String,
        completionHandler: CompletionHandler<JokeSearchResult?>
    ): NativeCancellable
}

internal class JokeClientImpl(private val apiClient: ApiClient) :
    JokeClient {

    init {
        freeze()
    }

    override fun getRandomJoke(
        category: JokeCategory?,
        completionHandler: CompletionHandler<Joke?>
    ) =
        iosScope.withNativeCompletionHandler(completionHandler) {
            apiClient.getRandomJoke(category)
        }

    override fun getJokeCategories(completionHandler: CompletionHandler<List<JokeCategory>?>) =
        iosScope.withNativeCompletionHandler(completionHandler) {
            apiClient.getJokeCategories()
        }

    override fun findJoke(query: String, completionHandler: CompletionHandler<JokeSearchResult?>) =
        iosScope.withNativeCompletionHandler(completionHandler) {
            apiClient.findJoke(query)
        }

}