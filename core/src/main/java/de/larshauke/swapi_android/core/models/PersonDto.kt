package de.larshauke.swapi_android.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class PersonDto(
    override val url: Url,
    val name: String,
    val gender: String,
    val height: String,
    val mass: String,
    @Json(name = "birth_year") val birthYear: String,
    @Json(name = "eye_color") val eyeColor: String,
    @Json(name = "hair_color") val hairColor: String,
    @Json(name = "skin_color") val skinColor: String,
    val homeworld: Url,
    val films: List<Url>,
    val species: List<Url>,
    val starships: List<Url>,
    val vehicles: List<Url>,
    val created: ZonedDateTime,
    val edited: ZonedDateTime
): SearchDto() {
    override val keys: List<String> =
        listOf(name)

    override val primaryName: String
        get() = name
}