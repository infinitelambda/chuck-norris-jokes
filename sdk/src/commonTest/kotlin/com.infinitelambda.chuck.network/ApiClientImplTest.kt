package com.infinitelambda.chuck.network

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private fun MockHttpClient(json: Json, handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData) =
    HttpClient(
        MockEngine(
            MockEngineConfig().apply {
                requestHandlers.add(handler)
            })
    ) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }


@ExperimentalCoroutinesApi
internal class ApiClientImplTest {

    private val json = Json

    @Test
    fun given_200OK_when_getRandomJoke_then_correctObjectReturned() = runTest {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/random") {
                respond(
                    json.encodeToString(serializer(), expectedJoke),
                    HttpStatusCode.OK,
                    headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualJoke = tested.getRandomJoke().getOrThrow()

        // then
        assertEquals(expectedJoke, actualJoke)
    }

    @Test
    fun given_400BadRequest_when_getRandomJoke_then_errorReturned() = runTest {
        // given
        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/random") {
                respondBadRequest()
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualError = tested.getRandomJoke().exceptionOrNull()

        // then
        assertNotNull(actualError)
    }

    @Test
    fun given_200OK_when_getRandomJokeForCategory_then_correctObjectReturned() = runTest {
        // given
        val expectedCategory = JokeCategory.CAREER

        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(expectedCategory)
        )

        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/random?category=${expectedCategory.name.lowercase()}") {
                respond(
                    json.encodeToString(serializer(), expectedJoke),
                    HttpStatusCode.OK,
                    headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualJoke = tested.getRandomJoke(expectedCategory).getOrThrow()

        // then
        assertEquals(expectedJoke, actualJoke)
    }

    @Test
    fun given_400BadRequest_when_getRandomJokeForCategory_then_errorReturned() = runTest {
        // given
        val expectedCategory = JokeCategory.CAREER

        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/random?category=${expectedCategory.name.lowercase()}") {
                respondBadRequest()
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualError = tested.getRandomJoke().exceptionOrNull()

        // then
        assertNotNull(actualError)
    }


    @Test
    fun given_200OK_when_getJokeCategories_then_listOfCategoriesReturned() = runTest {
        // given
        val expectedCategories = listOf(JokeCategory.CAREER, JokeCategory.ANIMAL)

        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/categories") {
                respond(
                    json.encodeToString(serializer(), expectedCategories),
                    HttpStatusCode.OK,
                    headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualCategories = tested.getJokeCategories().getOrThrow()

        // then
        assertEquals(expectedCategories, actualCategories)
    }

    @Test
    fun given_400BadRequest_when_getJokeCategories_then_errorReturned() = runTest {
        // given
        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/categories") {
                respondBadRequest()
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualError = tested.getJokeCategories().exceptionOrNull()

        // then
        assertNotNull(actualError)
    }

    @Test
    fun given_200OK_when_findJoke_then_correctObjectReturned() = runTest {
        // given
        val expectedJokeSearchResult = JokeSearchResult(
            total = 1,
            result = listOf(
                Joke(
                    iconUrl = "fakeIconUrl",
                    id = "fakeId",
                    value = "fakeValue",
                    url = "fakeUrl",
                    categories = listOf(JokeCategory.CAREER)
                )
            )
        )

        val expectedQuery = "fake"

        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/search?query=$expectedQuery") {
                respond(
                    json.encodeToString(serializer(), expectedJokeSearchResult),
                    HttpStatusCode.OK,
                    headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualJoke = tested.findJoke(expectedQuery).getOrThrow()

        // then
        assertEquals(expectedJokeSearchResult, actualJoke)
    }

    @Test
    fun given_400BadRequest_when_findJoke_then_errorReturned() = runTest {
        // given
        val expectedQuery = "fake"

        val mockHttpClient = MockHttpClient(json) { request ->
            if (request.url.toString() == "https://api.chucknorris.io/jokes/search?query=$expectedQuery") {
                respondBadRequest()
            } else {
                throw AssertionError("Unexpected request received: ${request.url}")
            }
        }

        val tested = ApiClientImpl(mockHttpClient)

        // when
        val actualError = tested.findJoke(expectedQuery).exceptionOrNull()

        // then
        assertNotNull(actualError)
    }

}