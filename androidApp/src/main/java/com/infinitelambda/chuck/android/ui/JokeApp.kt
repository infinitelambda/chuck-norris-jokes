package com.infinitelambda.chuck.android.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infinitelambda.chucknorris.client.android.ui.theme.AppTheme

@Composable
fun JokeApp(
    viewModelFactory: ViewModelFactory
) {
    AppTheme {
        JokeScreen(viewModelFactory = viewModelFactory)
    }
}