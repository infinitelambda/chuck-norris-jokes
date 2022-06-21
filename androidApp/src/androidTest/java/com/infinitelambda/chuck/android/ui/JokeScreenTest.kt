package com.infinitelambda.chuck.android.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import com.infinitelambda.chucknorris.client.android.ui.theme.AppTheme
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class JokeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val emptyViewModelFactory = EmptyViewModelFactory()

    private var mockState = JokeUiState()
    private val viewModel = mockk<JokeViewModel>()

    @Test
    fun given_jokeLoaded_then_jokePresented() {
        // given
        val expectedJoke = Joke(
            iconUrl = "fakeIconUrl",
            id = "fakeId",
            value = "fakeValue",
            url = "fakeUrl",
            categories = listOf(JokeCategory.CAREER)
        )

        every { viewModel.uiState } returns mockState
        every { viewModel.onViewCreated() } answers {
            mockState = mockState.copy(isLoading = false, joke = expectedJoke)
        }

        // when
        composeTestRule.setContent {
            AppTheme {
                JokeScreen(
                    viewModelFactory = emptyViewModelFactory,
                    viewModel = viewModel
                )
            }
        }

        // then
        composeTestRule.onNodeWithTag("jokeValue").assert(hasText(expectedJoke.value))
    }
}

private class EmptyViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        throw UnsupportedOperationException("Should never be called")
    }

}