package com.infinitelambda.chuck.android.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinitelambda.chuck.JokeClient
import com.infinitelambda.chuck.data.Joke
import com.infinitelambda.chuck.data.JokeCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class JokeUiState(
    val isLoading: Boolean = false,
    val jokeCategories: List<JokeCategory> = emptyList(),
    val jokeCategory: JokeCategory? = null,
    val joke: Joke? = null,
    val hasError: Boolean = false
)

class JokeViewModel(
    private val jokeClient: JokeClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    var uiState by mutableStateOf(JokeUiState(isLoading = true))
        private set

    fun onViewCreated() = viewModelScope.launch {
        uiState = uiState.copy(
            isLoading = false,
            jokeCategories = loadJokeCategories(),
            joke = loadRandomJoke(null)
        )
    }

    private suspend fun loadJokeCategories(): List<JokeCategory> {
        val categories = withContext(ioDispatcher) {
            jokeClient.getJokeCategories()
        }

        return categories.getOrElse { emptyList() }
    }

    private suspend fun loadRandomJoke(jokeCategory: JokeCategory?): Joke? {
        val joke = withContext(ioDispatcher) {
            jokeClient.getRandomJoke(jokeCategory)
        }

        return joke.getOrNull()
    }

    fun onJokeCategoryChanged(jokeCategory: JokeCategory?) = viewModelScope.launch {
        uiState = uiState.copy(
            jokeCategory = jokeCategory,
            joke = loadRandomJoke(jokeCategory)
        )
    }

    fun onRefreshJokeClicked() = viewModelScope.launch {
        uiState = uiState.copy(isLoading = true)

        uiState = uiState.copy(
            isLoading = false,
            joke = loadRandomJoke(uiState.jokeCategory)
        )
    }
}