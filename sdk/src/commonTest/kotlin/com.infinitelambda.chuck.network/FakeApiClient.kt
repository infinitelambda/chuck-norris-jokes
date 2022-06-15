package com.infinitelambda.chuck.network

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult

class FakeApiClient(
    private val getRandomJokeResult: Result<Joke>? = null,
    private val assertGetRandomJokeParameters: (JokeCategory?) -> Unit = { },
    private val getJokeCategoriesResult: Result<List<JokeCategory>>? = null,
    private val findJokeResult: Result<JokeSearchResult>? = null,
    private val assertFindJokeParameters: (String) -> Unit = {}
) : ApiClient {

    override suspend fun getRandomJoke(category: JokeCategory?): Result<Joke> {
        assertGetRandomJokeParameters(category)

        return getRandomJokeResult ?: throw IllegalStateException("getRandomJoke() not mocked!")
    }

    override suspend fun getJokeCategories(): Result<List<JokeCategory>> =
        getJokeCategoriesResult ?: throw IllegalStateException("getJokeCategories not mocked")

    override suspend fun findJoke(query: String): Result<JokeSearchResult> {
        assertFindJokeParameters(query)

        return findJokeResult ?: throw IllegalStateException("findJoke not mocked!")
    }

}