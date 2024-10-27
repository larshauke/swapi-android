package de.larshauke.swapi_android.core.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.larshauke.swapi_android.core.data.util.Url
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class PlanetDto(
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
    val url: Url,
    val created: ZonedDateTime,
    val edited: ZonedDateTime
): SearchDto() {
    override val key: List<String> =
        listOf(name)
}
