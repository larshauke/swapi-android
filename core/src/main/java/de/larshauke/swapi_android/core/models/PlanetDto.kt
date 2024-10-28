package de.larshauke.swapi_android.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class PlanetDto(
    override val url: Url,
    val name: String,
    val diameter: String,
    @Json(name = "rotation_period") val rotationPeriod: String,
    @Json(name = "orbital_period") val orbitalPeriod: String,
    val gravity: String,
    val population: String,
    val climate: String,
    val terrain: String,
    @Json(name = "surface_water") val surfaceWater: String,
    val residents: List<Url>,
    val films: List<Url>,
    val created: ZonedDateTime,
    val edited: ZonedDateTime
): SearchDto() {
    override val keys: List<String> =
        listOf(name)

    override val primaryName: String
        get() = name
}
