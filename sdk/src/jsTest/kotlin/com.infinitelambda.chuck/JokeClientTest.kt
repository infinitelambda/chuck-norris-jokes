package com.infinitelambda.chuck

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult
import com.infinitelambda.chuck.network.FakeApiClient
import com.infinitelambda.chuck.util.assertCatch
import com.infinitelambda.chuck.util.assertValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@ExperimentalJsExport
@ExperimentalCoroutinesApi
class JokeClientTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun given_successReturned_when_getRandomJoke_then_correctPromiseResult() {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val apiClient = FakeApiClient(getRandomJokeResult = Result.success(expectedJoke),
            assertGetRandomJokeParameters = { assertNull(it) })
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.getRandomJoke()

        // then
        promise.assertValue(expectedJoke)
    }

    @Test
    fun given_failureReturned_when_getRandomJoke_then_correctPromiseResult() {
        // given
        val expectedException = RuntimeException()

        val apiClient = FakeApiClient(
            getRandomJokeResult = Result.failure(expectedException),
            assertGetRandomJokeParameters = { assertNull(it) }
        )
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.getRandomJoke()

        // then
        promise.assertCatch(expectedException)
    }

    @Test
    fun given_successReturned_when_getRandomJokeWithCategory_then_correctPromiseResult() {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val expectedCategory = JokeCategory.CAREER

        val apiClient = FakeApiClient(
            getRandomJokeResult = Result.success(expectedJoke),
            assertGetRandomJokeParameters = { assertEquals(expectedCategory, it) }
        )
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.getRandomJoke(expectedCategory.name.lowercase())

        // then
        promise.assertValue(expectedJoke)
    }

    @Test
    fun given_failureReturned_when_getRandomJokeWithCategory_then_correctPromiseResult() {
        // given
        val expectedException = RuntimeException()

        val expectedCategory = JokeCategory.CAREER

        val apiClient = FakeApiClient(
            getRandomJokeResult = Result.failure(expectedException),
            assertGetRandomJokeParameters = { assertEquals(expectedCategory, it) }
        )
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.getRandomJoke(expectedCategory.name.lowercase())

        // then
        promise.assertCatch(expectedException)
    }

    @Test
    fun given_successReturned_when_getJokeCategories_then_correctPromiseResult() {
        // given
        val expectedCategories = listOf(JokeCategory.ANIMAL, JokeCategory.EXPLICIT)

        val apiClient = FakeApiClient(
            getJokeCategoriesResult = Result.success(expectedCategories)
        )
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.getJokeCategories()

        // then
        promise.assertValue(expectedCategories)
    }

    @Test
    fun given_failureReturned_when_getJokeCategories_then_correctPromiseResult() {
        // given
        val expectedException = RuntimeException()

        val apiClient = FakeApiClient(
            getJokeCategoriesResult = Result.failure(expectedException)
        )
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.getJokeCategories()

        // then
        promise.assertCatch(expectedException)
    }

    @Test
    fun given_successReturned_when_findJoke_then_correctPromiseResult() {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val expectedSearchResult = JokeSearchResult(
            total = 1, result = listOf(expectedJoke)
        )

        val expectedQuery = "test"

        val apiClient = FakeApiClient(
            findJokeResult = Result.success(expectedSearchResult),
            assertFindJokeParameters = { assertEquals(expectedQuery, it) }
        )
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.findJoke(expectedQuery)

        // then
        promise.assertValue(expectedSearchResult)
    }

    @Test
    fun given_failureReturned_when_findJoke_then_correctPromiseResult() {
        // given
        val expectedException = RuntimeException()

        val expectedQuery = "test"

        val apiClient = FakeApiClient(
            findJokeResult = Result.failure(expectedException),
            assertFindJokeParameters = { assertEquals(expectedQuery, it) }
        )
        val tested = JokeClientImpl(apiClient)

        // when
        val promise = tested.findJoke(expectedQuery)

        // then
        promise.assertCatch(expectedException)
    }

}