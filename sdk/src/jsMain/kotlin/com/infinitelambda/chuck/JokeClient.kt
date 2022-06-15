package com.infinitelambda.chuck

import com.infinitelambda.chuck.data.JokeJs
import com.infinitelambda.chuck.data.JokeSearchResultJs
import com.infinitelambda.chuck.data.asJokeCategory
import com.infinitelambda.chuck.data.toJs
import com.infinitelambda.chuck.network.ApiClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.promise
import kotlin.js.ExperimentalJsExport
import kotlin.js.Promise

private val jsScope = MainScope()

@ExperimentalJsExport
@JsExport
@JsName("JokeClient")
fun jokeClient(config: JokeClientConfig?) =
    JokeClient(
        ApiClient.create(
            isLoggingEnabled = config?.isLoggingEnabled ?: false,
            requestTimeout = config?.requestTimeout?.toLong() ?: 10000,
            connectTimeout = config?.connectTimeout?.toLong() ?: 10000
        )
    )

open external class JokeClientConfig {
    var isLoggingEnabled: Boolean?
    var requestTimeout: Int?
    var connectTimeout: Int?
}

@ExperimentalJsExport
@JsExport
@JsName("JokeClientImpl")
class JokeClient internal constructor(private val apiClient: ApiClient) {

    fun getRandomJoke(category: String?): Promise<JokeJs> =
        jsScope.promise { apiClient.getRandomJoke(category.asJokeCategory()).getOrThrow().toJs() }

    fun getJokeCategories(): Promise<Array<String>> =
        jsScope.promise {
            apiClient.getJokeCategories().getOrThrow().map { it.name.lowercase() }.toTypedArray()
        }

    fun findJoke(query: String): Promise<JokeSearchResultJs> =
        jsScope.promise { apiClient.findJoke(query).getOrThrow().toJs() }

}