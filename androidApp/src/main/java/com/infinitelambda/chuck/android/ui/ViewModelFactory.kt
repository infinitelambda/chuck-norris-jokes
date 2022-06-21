package com.infinitelambda.chuck.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infinitelambda.chuck.JokeClient
import java.lang.IllegalArgumentException

class ViewModelFactory(private val jokeClient: JokeClient) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            JokeViewModel::class.java -> JokeViewModel(jokeClient)
            else -> throw IllegalArgumentException("Unknown view model class: ${modelClass.canonicalName}")
        } as T

}