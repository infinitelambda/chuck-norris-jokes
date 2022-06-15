package com.infinitelambda.chuck.data

import kotlin.js.ExperimentalJsExport

@ExperimentalJsExport
@JsExport
@JsName("Joke")
data class JokeJs(
    val iconUrl: String,
    val id: String,
    val url: String,
    val value: String,
    val categories: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as JokeJs

        if (iconUrl != other.iconUrl) return false
        if (id != other.id) return false
        if (url != other.url) return false
        if (value != other.value) return false
        if (!categories.contentEquals(other.categories)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = iconUrl.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + categories.contentHashCode()
        return result
    }
}

@ExperimentalJsExport
internal fun Joke.toJs() =
    JokeJs(
        iconUrl = this.iconUrl,
        id = this.id,
        url = this.url,
        value = this.value,
        categories = this.categories.map { it.name.lowercase() }.toTypedArray()
    )

internal fun String?.asJokeCategory(): JokeCategory? =
    this?.let {
        kotlin.runCatching { JokeCategory.valueOf(it.uppercase()) }
            .getOrDefault(null)
    }

@ExperimentalJsExport
@JsExport
@JsName("JokeSearchResult")
data class JokeSearchResultJs(
    val total: Int,
    val result: Array<JokeJs>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as JokeSearchResultJs

        if (total != other.total) return false
        if (!result.contentEquals(other.result)) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = total
        result1 = 31 * result1 + result.contentHashCode()
        return result1
    }
}

@ExperimentalJsExport
internal fun JokeSearchResult.toJs() =
    JokeSearchResultJs(
        total = this.total,
        result = this.result.map { it.toJs() }.toTypedArray()
    )