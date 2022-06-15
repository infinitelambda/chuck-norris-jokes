package com.infinitelambda.chuck.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.js.JsName

@Serializable
@JsName("JokeKotlin")
data class Joke(
    @SerialName("icon_url") val iconUrl: String,
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("value") val value: String,
    @SerialName("categories") val categories: List<JokeCategory>
)

@Serializable(with = JokeCategorySerializer::class)
enum class JokeCategory {
    ANIMAL,
    CAREER,
    CELEBRITY,
    DEV,
    EXPLICIT,
    FASHION,
    FOOD,
    HISTORY,
    MONEY,
    MOVIE,
    MUSIC,
    POLITICAL,
    RELIGION,
    SCIENCE,
    SPORT,
    TRAVEL,
    UNKNOWN
}

object JokeCategorySerializer : KSerializer<JokeCategory> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.infinitelambda.chuck.data.JokeCategorySerializer",
            PrimitiveKind.STRING
        )

    override fun deserialize(decoder: Decoder): JokeCategory =
        kotlin.runCatching { JokeCategory.valueOf(decoder.decodeString().uppercase()) }
            .getOrDefault(JokeCategory.UNKNOWN)

    override fun serialize(encoder: Encoder, value: JokeCategory) {
        encoder.encodeString(value.name.lowercase())
    }
}

@Serializable
@JsName("JokeSearchResultKotlin")
data class JokeSearchResult(
    @SerialName("total") val total: Int,
    @SerialName("result") val result: List<Joke>
)

