package de.larshauke.swapi_android.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime


@JsonClass(generateAdapter = true)
data class StarshipDto(
    override val url: Url,
    val name: String,
    val model: String,
    @Json(name = "starship_class") val starshipClass: String,
    val manufacturer: String,
    @Json(name = "cost_in_credits") val costInCredits: String,
    val length: String,
    val crew: String,
    val passengers: String,
    @Json(name = "max_atmosphering_speed") val maxAtmospheringSpeed: String,
    @Json(name = "hyperdrive_rating") val hyperdriveRating: String,
    @Json(name = "MGLT") val mglt: String,
    @Json(name = "cargo_capacity") val cargoCapacity: String,
    val consumables: String,
    val films: List<Url>,
    val pilots: List<Url>,
    val created: ZonedDateTime,
    val edited: ZonedDateTime
): SearchDto() {
    override val keys: List<String> =
        listOf(name, model)

    override val primaryName: String
        get() = name

    override val secondaryName: String
        get() = model
}