package com.infinitelambda.chuck.android.ui

import androidx.annotation.StringRes
import com.infinitelambda.chuck.android.R
import com.infinitelambda.chuck.data.JokeCategory

@StringRes
fun JokeCategory?.mapToStringResource(): Int =
    when (this) {
        JokeCategory.ANIMAL -> R.string.joke_category_animal
        JokeCategory.CAREER -> R.string.joke_category_career
        JokeCategory.CELEBRITY -> R.string.joke_category_celebrity
        JokeCategory.DEV -> R.string.joke_category_dev
        JokeCategory.EXPLICIT -> R.string.joke_category_explicit
        JokeCategory.FASHION -> R.string.joke_category_fashion
        JokeCategory.FOOD -> R.string.joke_category_food
        JokeCategory.HISTORY -> R.string.joke_category_history
        JokeCategory.MONEY -> R.string.joke_category_money
        JokeCategory.MOVIE -> R.string.joke_category_movie
        JokeCategory.MUSIC -> R.string.joke_category_music
        JokeCategory.POLITICAL -> R.string.joke_category_political
        JokeCategory.RELIGION -> R.string.joke_category_religion
        JokeCategory.SCIENCE -> R.string.joke_category_science
        JokeCategory.SPORT -> R.string.joke_category_sport
        JokeCategory.TRAVEL -> R.string.joke_category_travel
        JokeCategory.UNKNOWN -> R.string.joke_category_unknown
        null -> R.string.joke_category_any
    }