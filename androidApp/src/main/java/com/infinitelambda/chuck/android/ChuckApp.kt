package com.infinitelambda.chuck.android

import android.app.Application
import com.infinitelambda.chuck.BuildConfig
import com.infinitelambda.chuck.JokeClient
import com.infinitelambda.chuck.android.ui.ViewModelFactory

class ChuckApp : Application() {

    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        val jokeClient = JokeClient {
            isLoggingEnabled = BuildConfig.DEBUG
        }

        viewModelFactory = ViewModelFactory(jokeClient)
    }

}