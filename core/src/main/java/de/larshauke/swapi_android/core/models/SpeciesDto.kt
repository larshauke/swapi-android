package de.larshauke.swapi_android.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class SpeciesDto(
    override val url: Url,
    val name: String,
    val classification: String,
    val designation: String,
    @Json(name = "average_height") val averageHeight: String,
    @Json(name = "average_lifespan") val averageLifespan: String,
    @Json(name = "eye_colors") val eyeColors: String,
    @Json(name = "hair_colors") val hairColors: String,
    @Json(name = "skin_colors") val skinColors: String,
    val language: String,
    val homeworld: Url,
    val people: List<Url>,
    val films: List<Url>,
    val created: ZonedDateTime,
    val edited: ZonedDateTime
): SearchDto() {
    override val keys: List<String> =
        listOf(name)

    override val primaryName: String
        get() = name
}
