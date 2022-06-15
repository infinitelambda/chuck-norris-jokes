package com.infinitelambda.chuck

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult
import com.infinitelambda.chuck.network.FakeApiClient
import com.infinitelambda.chuck.util.TestCompletionHandler
import com.infinitelambda.chuck.util.asNSError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.native.concurrent.isFrozen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class JokeClientImplTest {

    @Test
    fun assert_client_frozen() {
        val tested = JokeClientImpl(FakeApiClient())
        assertTrue(tested.isFrozen)
    }

    @Test
    fun given_success_when_getRandomJoke_then_correctCallbackInvoked() {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = Result.success(expectedJoke),
            assertGetRandomJokeParameters = { assertNull(it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler = TestCompletionHandler(expectedJoke)

        // when
        tested.getRandomJoke(completionHandler = completionHandler)
    }

    @Test
    fun given_failure_when_getRandomJoke_then_correctCallbackInvoked() {
        // given
        val expectedError = RuntimeException()

        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = Result.failure(expectedError),
            assertGetRandomJokeParameters = { assertNull(it) }
        )

        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler =
            TestCompletionHandler<Joke>(expectedError = expectedError.asNSError())

        // when
        tested.getRandomJoke(completionHandler = completionHandler)
    }

    @Test
    fun given_success_when_getRandomJokeWithCategory_then_correctCallbackInvoked() {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val expectedCategory = JokeCategory.CAREER

        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = Result.success(expectedJoke),
            assertGetRandomJokeParameters = { assertEquals(expectedCategory, it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler = TestCompletionHandler(expectedJoke)

        // when
        tested.getRandomJoke(expectedCategory, completionHandler)
    }

    @Test
    fun given_failure_when_getRandomJokeWithCategory_then_correctCallbackInvoked() {
        // given
        val expectedError = RuntimeException()

        val expectedCategory = JokeCategory.CAREER

        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = Result.failure(expectedError),
            assertGetRandomJokeParameters = { assertEquals(expectedCategory, it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler =
            TestCompletionHandler<Joke>(expectedError = expectedError.asNSError())

        // when
        tested.getRandomJoke(expectedCategory, completionHandler)
    }

    @Test
    fun given_success_when_getJokeCategories_then_correctCallbackInvoked() {
        // given
        val expectedCategories = listOf(JokeCategory.CAREER, JokeCategory.ANIMAL)

        val fakeApiClient = FakeApiClient(
            getJokeCategoriesResult = Result.success(expectedCategories)
        )
        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler = TestCompletionHandler(expectedCategories)

        // when
        tested.getJokeCategories(completionHandler)
    }

    @Test
    fun given_failure_when_getJokeCategories_then_correctCallbackInvoked() {
        // given
        val expectedError = RuntimeException()

        val fakeApiClient = FakeApiClient(
            getJokeCategoriesResult = Result.failure(expectedError)
        )
        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler =
            TestCompletionHandler<List<JokeCategory>>(expectedError = expectedError.asNSError())

        // when
        tested.getJokeCategories(completionHandler)
    }

    @Test
    fun given_success_when_findJoke_then_correctCallbackInvoked() {
        // given
        val expectedResult = JokeSearchResult(
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

        val expectedQuery = "test"

        val fakeApiClient = FakeApiClient(
            findJokeResult = Result.success(expectedResult),
            assertFindJokeParameters = { assertEquals(expectedQuery, it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler = TestCompletionHandler(expectedResult)

        // when
        tested.findJoke(expectedQuery, completionHandler)
    }

    @Test
    fun given_failure_when_findJoke_then_correctCallbackInvoked() {
        // given
        val expectedError = RuntimeException()

        val expectedQuery = "test"

        val fakeApiClient = FakeApiClient(
            findJokeResult = Result.failure(expectedError),
            assertFindJokeParameters = { assertEquals(expectedQuery, it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        val completionHandler =
            TestCompletionHandler<JokeSearchResult>(expectedError = expectedError.asNSError())

        // when
        tested.findJoke(expectedQuery, completionHandler)
    }
}