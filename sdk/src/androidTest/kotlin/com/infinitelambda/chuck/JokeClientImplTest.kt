package com.infinitelambda.chuck

import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chuck.data.JokeSearchResult
import com.infinitelambda.chuck.network.FakeApiClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class JokeClientImplTest {

    @Test
    fun `given success when getRandomJoke then correct value returned`() = runTest {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val expectedTry = Result.success(expectedJoke)
        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = expectedTry,
            assertGetRandomJokeParameters = { assertNull(it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.getRandomJoke()

        // then
        assertEquals(expectedTry, actualTry)
    }

    @Test
    fun `given failure when getRandomJoke then correct value returned`() = runTest {
        // given
        val expectedError = RuntimeException()

        val expectedTry = Result.failure<Joke>(expectedError)
        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = expectedTry,
            assertGetRandomJokeParameters = { assertNull(it) }
        )

        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.getRandomJoke()

        // then
        assertEquals(expectedTry, actualTry)
    }

    @Test
    fun `given success when getRandomJoke with category then correct value returned`() = runTest {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        val expectedCategory = JokeCategory.CAREER

        val expectedTry = Result.success(expectedJoke)
        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = expectedTry,
            assertGetRandomJokeParameters = { assertEquals(expectedCategory, it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.getRandomJoke(expectedCategory)

        // then
        assertEquals(expectedTry, actualTry)
    }

    @Test
    fun `given failure when getRandomJoke with category then correct value returned`() = runTest {
        // given
        val expectedError = RuntimeException()

        val expectedCategory = JokeCategory.CAREER

        val expectedTry = Result.failure<Joke>(expectedError)
        val fakeApiClient = FakeApiClient(
            getRandomJokeResult = expectedTry,
            assertGetRandomJokeParameters = { assertEquals(expectedCategory, it) }
        )

        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.getRandomJoke(expectedCategory)

        // then
        assertEquals(expectedTry, actualTry)
    }

    @Test
    fun `given success when getJokeCategories then correct value returned`() = runTest {
        // given
        val expectedCategories = listOf(JokeCategory.CAREER, JokeCategory.ANIMAL)

        val expectedTry = Result.success(expectedCategories)
        val fakeApiClient = FakeApiClient(
            getJokeCategoriesResult = expectedTry
        )
        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.getJokeCategories()

        // then
        assertEquals(expectedTry, actualTry)
    }

    @Test
    fun `given failure when getJokeCategories then correct value returned`() = runTest {
        // given
        val expectedError = RuntimeException()

        val expectedTry = Result.failure<List<JokeCategory>>(expectedError)
        val fakeApiClient = FakeApiClient(
            getJokeCategoriesResult = expectedTry
        )
        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.getJokeCategories()

        // then
        assertEquals(expectedTry, actualTry)
    }

    @Test
    fun `given success when findJoke then correct value returned`() = runTest {
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

        val expectedTry = Result.success(expectedResult)
        val fakeApiClient = FakeApiClient(
            findJokeResult = expectedTry,
            assertFindJokeParameters = { assertEquals(expectedQuery, it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.findJoke(expectedQuery)

        // then
        assertEquals(actualTry, expectedTry)
    }

    @Test
    fun `given failure when findJoke then correct value returned`() = runTest {
        // given
        val expectedError = RuntimeException()

        val expectedQuery = "test"

        val expectedTry = Result.failure<JokeSearchResult>(expectedError)
        val fakeApiClient = FakeApiClient(
            findJokeResult = expectedTry,
            assertFindJokeParameters = { assertEquals(expectedQuery, it) }
        )
        val tested = JokeClientImpl(fakeApiClient)

        // when
        val actualTry = tested.findJoke(expectedQuery)

        // then
        assertEquals(expectedTry, actualTry)
    }
}