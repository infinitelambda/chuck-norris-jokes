package com.infinitelambda.chuck.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.infinitelambda.chuck.android.ui.JokeApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = (application as ChuckApp).viewModelFactory

        setContent {
            JokeApp(viewModelFactory)
        }
    }
}
